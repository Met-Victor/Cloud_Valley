package cn.com.post.service;

import cn.com.core.post.dto.MessageDTO;
import cn.com.core.post.po.Message;
import cn.com.core.util.Result;
import cn.com.post.config.rabbitmq.RabbitMQConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dashengz
 * @since 2024-04-16
 */
public interface IMessageService extends IService<Message> {

    /**
     * 获取消息列表
     *
     * @return {@link Result}
     */
    Result getMessageList(MessageDTO messageDTO);
}
