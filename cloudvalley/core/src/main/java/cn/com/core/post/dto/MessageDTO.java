package cn.com.core.post.dto;

import cn.com.core.post.po.Message;
import cn.com.core.util.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @className: MessageDTO
 * @author: Met.
 * @date: 2024/4/19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDTO extends BaseRequest<Message> {

    @ApiModelProperty(value = "消息类型")
    @NotBlank
    private String type;
}
