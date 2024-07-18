package cn.com.core.util;


import cn.com.core.system.po.Role;
import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

/**
 * @className: JwtUser
 * @author: Met.
 * @date: 2024/3/2
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtUser {
    private String password;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private String avatar;
    private String id; // 或者使用 Integer，根据实际情况
    private String email;
    private boolean enabled;
    private boolean accountNonLocked;
    private String username;
    private String nickname;
    private String description;
    private String isLocked;
    private String roleId;
    private String roleName;
    private String roleCode;
    private String roleLabel;


}
