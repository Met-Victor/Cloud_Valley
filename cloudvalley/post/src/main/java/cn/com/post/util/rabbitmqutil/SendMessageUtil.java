package cn.com.post.util.rabbitmqutil;

import cn.com.core.post.po.Message;
import cn.com.post.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: SendMessageUtils
 * @author: Met.
 * @date: 2024/4/16
 **/
@Service
public class SendMessageUtil {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送评论消息通知
     *
     * @param message
     */
    public void sendCommentMessage(Message message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.BASIC_MESSAGE_QUEUE, message);
    }
}
