package cn.com.core.post.vo;

import cn.com.core.system.po.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: UserVo
 * @author: Met.
 * @date: 2024/3/16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "签名")
    private String description;

    @ApiModelProperty(value = "邮箱")
    private String email;

    public static UserVO from(User user) {
        return UserVO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .description(user.getDescription())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .build();
    }
}
