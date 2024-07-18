package cn.com.core.system.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className: UserChangeInfoReq
 * @author: Met.
 * @date: 2024/4/21
 **/
@Data
public class UserChangePasswordReq {
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
