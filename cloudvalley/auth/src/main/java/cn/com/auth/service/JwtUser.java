package cn.com.auth.service;

import cn.com.core.system.po.Role;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @className: JwtUser
 * @author: Met.
 * @date: 2024/3/2
 **/
@Data
public class JwtUser implements UserDetails {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "签名")
    private String description;

    @ApiModelProperty(value = "是否锁定")
    private String isLocked;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色code")
    private String roleCode;

    @ApiModelProperty(value = "是否锁定")
    private String roleLabel;


    public JwtUser(String id, String username, String password, String email, String avatar, String description, String nickname, Role role, String isAccountNoLocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.description = description;
        this.nickname = nickname;
        this.roleId = role.getId();
        this.roleLabel = role.getLabel();
        this.roleCode = role.getCode();
        this.isLocked = isAccountNoLocked;
    }

    // 1 true 0 false
    @JSONField(serialize = false) // 忽略转json
    @ApiModelProperty(value = "帐户是否过期(1 未过期，0已过期)")
    private boolean isAccountNonExpired; // 不要写小写 boolean

    @JSONField(serialize = false) // 忽略转json
    @ApiModelProperty(value = "帐户是否被锁定(1 未过期，0已过期)")
    private boolean isAccountNonLocked;

    @JSONField(serialize = false) // 忽略转json
    @ApiModelProperty(value = "密码是否过期(1 未过期，0已过期)")
    private boolean isCredentialsNonExpired;

    @JSONField(serialize = false) // 忽略转json
    @ApiModelProperty(value = "帐户是否可用(1 可用，0 删除用户)")
    private boolean isEnabled;

    @Override
    public boolean isAccountNonLocked() {
        // 返回 true 表示用户未被锁定
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 返回 true 表示用户已启用
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 封装用户拥有的菜单权限标识
     */
    @JSONField(serialize = false) // 忽略转json
    private List<GrantedAuthority> authorities;


}
