package cn.com.core.system.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: UserChangeInfoReq
 * @author: Met.
 * @date: 2024/4/22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeInfoReq {
    private String userId;
    private String nickname;
    private String email;
    private String avatar;
    private String description;
}
