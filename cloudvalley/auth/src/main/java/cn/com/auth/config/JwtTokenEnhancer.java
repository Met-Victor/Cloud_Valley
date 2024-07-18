package cn.com.auth.config;

import cn.com.auth.service.RedisKeyConstant;
import cn.com.auth.service.JwtUser;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: JwtTokenEnhancer
 * @author: Met.
 * @date: 2024/3/2
 **/
@Slf4j
@Configuration
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        JwtUser user = (JwtUser) oAuth2Authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", JSON.toJSON(user));

        // 设置附加信息
        ( (DefaultOAuth2AccessToken)oAuth2AccessToken ).setAdditionalInformation(map);

        //jti增加前缀
        String jti = oAuth2AccessToken.getValue();
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setValue(jti + RedisKeyConstant.POSTFIX_USER_TOKEN_JTI);
        return oAuth2AccessToken;
    }
}
