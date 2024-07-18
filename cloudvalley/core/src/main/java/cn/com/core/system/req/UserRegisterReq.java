package cn.com.core.system.req;

import cn.com.core.system.po.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @className: UserRegisterReq
 * @author: Met.
 * @date: 2024/3/16
 **/
@Data
public class UserRegisterReq {

    private String username;

    private String password;

    public User covert(UserRegisterReq userRegisterReq) {
        return User
                .builder()
                .name(userRegisterReq.getUsername())
                .password(userRegisterReq.getPassword())
                .build();
    }
}
