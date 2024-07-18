package cn.com.post.service.impl;

import cn.com.core.feign.IFeignSystemController;
import cn.com.core.post.po.Follow;
import cn.com.core.post.po.Message;
import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.core.util.JwtUser;
import cn.com.core.util.Result;
import cn.com.post.mapper.FollowMapper;
import cn.com.post.service.IFollowService;
import cn.com.post.util.rabbitmqutil.SendMessageUtil;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户关注表 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-11
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFeignSystemController feignSystemController;

    @Resource
    private SendMessageUtil sendMessageUtil;

    @Override
    public Result addFollow(String noticerId) {
        logger.debug("FollowServiceImpl.addFollow:{}", noticerId);
        JwtUser userInfo = AuthUtil.getUserInfo();
        if (userInfo == null) {
            return Result.error(Constant.RESULT_USER_NO_LOGIN);
        }
        if (userInfo.getId().equals(noticerId)) {
            Result.error(Constant.RESULT_USER_CANNOT_FOLLOW_SELF);
        }
        User user = feignSystemController.getUserById(noticerId);
        if (user == null) {
            return Result.error(Constant.RESULT_USER_NO_EXISTENCE);
        }
        if (isFollowed(userInfo.getId(), noticerId)) {
            return Result.error(Constant.RESULT_FOLLOW_IS_EXIST);
        }
        boolean flag = save(Follow
                .builder()
                .userId(userInfo.getId())
                .noticer(noticerId)
                .build()
        );
        if (flag) {
            sendMessageUtil.sendCommentMessage(Message
                    .builder()
                    .senderId(noticerId)
                    .receiverId(userInfo.getId())
                    .type(Constant.DICT_MESSAGE_TYPE_LIKE)
                    .content(Constant.MESSAGE_LIKE_CONTENT)
                    .build());
        }
        return flag ? Result.ok(Constant.RESULT_FOLLOW_SUCCESS) : Result.error(Constant.RESULT_FOLLOW_ERROR);
    }

    @Override
    public Result cancelFollow(String noticerId) {
        logger.debug("FollowServiceImpl.cancelFollow:{}", noticerId);
        JwtUser userInfo = AuthUtil.getUserInfo();
        if (userInfo == null) {
            return Result.error(Constant.RESULT_USER_NO_LOGIN);
        }
        boolean flag = remove(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getUserId, userInfo.getId())
                        .eq(Follow::getNoticer, noticerId));
        return flag ? Result.ok(Constant.RESULT_CANCEL_FOLLOW_SUCCESS) : Result.error(Constant.RESULT_CANCEL_FOLLOW_ERROR);
    }

    @Override
    public Result getFollowList() {
        logger.debug("FollowServiceImpl.getFollowList");
        JwtUser userInfo = AuthUtil.getUserInfo();
        if (userInfo == null) {
            return Result.error(Constant.RESULT_USER_NO_LOGIN);
        }
        Map<String, List<UserVO>> userMap = new HashMap<>(10);
        List<String> userFollowIdList = list(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getUserId, userInfo.getId())
        ).stream()
                .map(Follow::getNoticer)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userFollowIdList)) {
            userMap.put("followList", new ArrayList<>());
        } else {
            List<UserVO> userInfoList = feignSystemController.getUserInfoList(userFollowIdList);
            userMap.put("followList", userInfoList);
        }
        List<String> userFansList = list(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getNoticer, userInfo.getId())
        ).stream()
                .map(Follow::getUserId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userFansList)) {
            userMap.put("fansList", new ArrayList<>());
        } else {
            List<UserVO> userInfoList = feignSystemController.getUserInfoList(userFansList);
            userMap.put("fansList", userInfoList);
        }
        return Result.ok(userMap);
    }

    @Override
    public boolean isFollowed(String userId, String noticerId) {
        return getBaseMapper().exists(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getUserId, userId)
                        .eq(Follow::getNoticer, noticerId)
        );
    }
}
