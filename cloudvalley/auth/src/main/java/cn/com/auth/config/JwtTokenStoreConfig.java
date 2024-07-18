package cn.com.auth.config;

import cn.com.auth.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedisConfig
 * @author: ZZZ
 * @date: 2023/8/31
 **/
@Configuration
public class JwtTokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Resource
    private RedisUtil redisUtil;

    //    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("appId"); // 设置用于签名的密钥，请更换成自己的密钥
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
        // 采用jwt管理信息
        return new JwtTokenStore(jwtAccessTokenConverter()) {
            // redis存储jwt令牌
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                // 将jwt中的唯一标识 jti 作为redis中的 key 值 ，value值是 accessToken 访问令牌
                if (token.getAdditionalInformation().containsKey("jti")) {
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    // 存储到redis中 (key, value, 有效时间，时间单位)
                    redisUtil.setEx(jti, token.getValue(), token.getExpiresIn(), TimeUnit.SECONDS);
                }
                super.storeAccessToken(token, authentication);
            }

            // 删除redis中的jwt令牌
            @Override
            public void removeAccessToken(OAuth2AccessToken token) {
                if (token.getAdditionalInformation().containsKey("jti")) {
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    // 将redis中对应jti的记录删除
                    redisUtil.deleteByKey(jti);
                }
                super.removeAccessToken(token);
            }
        };

        // 采用jwt管理信息
//        return new RedisTokenStore(redisConnectionFactory);
    }
}