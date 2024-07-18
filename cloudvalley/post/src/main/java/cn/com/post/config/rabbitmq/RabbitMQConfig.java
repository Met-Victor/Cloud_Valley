package cn.com.post.config.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: RabbitMQConfig
 * @author: Met.
 * @date: 2024/4/16
 **/
@Component
public class RabbitMQConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String BASIC_MESSAGE_QUEUE = "basic_message_queue";

    @Bean
    public Queue basicMessageQueue() {
        /*
        DEAD_LETTER_QUEUE: 这是队列的名称，可以根据实际情况替换需要创建的队列的名称。
        false: 第一个 false 表示队列不是持久化的。如果设置为 true，则表示队列是持久化的，在服务器重启后仍然存在。
        false: 第二个 false 表示队列不是独占的。如果设置为 true，则表示队列是独占的，只有创建它的连接可以使用。
        false: 第三个 false 表示队列不是自动删除的。如果设置为 true，则表示当最后一个消费者取消订阅时，队列会自动删除。
        args: 这是一个 Map 对象，可以用于设置队列的其他参数，如设置过期时间、死信交换器等。
        */
        logger.debug("queue is create: {}", BASIC_MESSAGE_QUEUE);
        return new Queue(BASIC_MESSAGE_QUEUE, false);
    }
}
