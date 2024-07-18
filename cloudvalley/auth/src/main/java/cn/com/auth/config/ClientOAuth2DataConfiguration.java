package cn.com.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @className: ClientOAuth2DataConfiguration
 * @author: ZZZ
 * @date: 2023/8/31
 **/
@Data
@Component
public class ClientOAuth2DataConfiguration {

    // 客户端标识 ID
    @Value("${client.oauth2.client-id}")
    private String clientId;


    // 客户端安全码
    @Value("${client.oauth2.secret}")
    private String secret;

    // 授权类型
    @Value("${client.oauth2.grant_types:password}")
    private String[] grantTypes;

    // token有效期
    @Value("${client.oauth2.token-validity-time}")
    private int tokenValidityTime;

    /**
     * refresh-token有效期
     */
    @Value("${client.oauth2.refresh-token-validity-time}")
    private int refreshTokenValidityTime;

    /**
     * 客户端访问范围
     */
    @Value("${client.oauth2.scopes:api}")
    private String[] scopes;

}