package cn.com.post.controller;


import cn.com.core.post.dto.MessageDTO;
import cn.com.core.util.Result;
import cn.com.post.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息模块
 *
 * @author dashengz
 * @since 2024-04-16
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private IMessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取消息列表根据类型
     *
     * @return {@link Result}
     */
    @PostMapping("/getMessageList")
    public Result getMessageList(@RequestBody MessageDTO messageDTO) {
        logger.debug("MessageController.getMessageList:{}", messageDTO);
        return messageService.getMessageList(messageDTO);
    }


}
