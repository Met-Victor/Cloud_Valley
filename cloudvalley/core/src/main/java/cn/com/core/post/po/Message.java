package cn.com.core.post.po;

import java.util.Date;
import java.io.Serializable;
import java.util.Map;

import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *
 * </p>
 *
 * @author dashengz
 * @since 2024-04-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("message")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Message对象", description = "message")
public class Message extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送者用户id")
    private String senderId;

    @ApiModelProperty(value = "接收者用户id")
    private String receiverId;

    @ApiModelProperty(value = "消息类型 参考字典表-MESSAGE_TYPE")
    private String type;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "用户信息")
    @TableField(exist = false)
    private UserVO userVO;
}
