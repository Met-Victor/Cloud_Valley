package cn.com.post.config.rabbitmq;

import cn.com.core.post.po.Message;
import cn.com.post.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: RabbltMQListener
 * @author: Met.
 * @date: 2024/4/16
 **/
@Service
public class RabbitMQListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IMessageService messageService;

    @RabbitListener(queues = RabbitMQConfig.BASIC_MESSAGE_QUEUE)
    private void listenBasicMessageQueue(Message message) {
        logger.debug("RabbitMQListener.listenBasicMessageQueue:{}", message);
        messageService.save(message);
    }
}
