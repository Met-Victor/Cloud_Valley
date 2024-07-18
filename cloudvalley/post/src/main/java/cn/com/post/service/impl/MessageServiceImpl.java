package cn.com.post.service.impl;

import cn.com.core.feign.IFeignSystemController;
import cn.com.core.post.dto.MessageDTO;
import cn.com.core.post.po.Message;
import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.SysDict;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.core.util.JwtUser;
import cn.com.core.util.Result;
import cn.com.post.mapper.MessageMapper;
import cn.com.post.service.IMessageService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.ref.PhantomReference;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-04-16
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFeignSystemController feignSystemController;

    @Override
    public Result getMessageList(MessageDTO messageDTO) {
        logger.debug("MessageServiceImpl.getMessageList:{}", messageDTO);
        JwtUser userInfo = AuthUtil.getUserInfo();
        IPage<Message> page = page(messageDTO.getPage(), new LambdaQueryWrapper<Message>()
                .eq(Message::getReceiverId, userInfo.getId())
                .orderByDesc(Message::getCreateTime)
        );
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return Result.ok(page);
        }
        Set<String> userIdSet = page.getRecords().stream()
                .map(Message::getSenderId)
                .collect(Collectors.toSet());
        Map<String, UserVO> userInfoMap = feignSystemController.getUserInfoMap(new ArrayList<>(userIdSet));
        page.getRecords().forEach(item -> {
            item.setUserVO(userInfoMap.getOrDefault(item.getSenderId(), null));
        });
        return Result.ok(page);
    }
}
