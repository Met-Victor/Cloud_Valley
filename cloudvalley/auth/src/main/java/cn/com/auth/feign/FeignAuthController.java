package cn.com.auth.feign;

import cn.com.core.feign.IFeignAuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @className: FeignAuthController
 * @author: Met.
 * @date: 2024/3/3
 **/
@RestController
public class FeignAuthController implements IFeignAuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void setUser(String token) {
        logger.debug("FeignAuthController.setUser:{}", token);
        extractUserDetails(token);
    }

    public void extractUserDetails(String token) {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        OAuth2AuthenticationDetails details =
                (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        Map<String, Object> map = (Map<String, Object>) details.getDecodedDetails();
        Map<String, String> userInfo = (Map<String, String>) map.get("userInfo");
        System.out.println(map);
        System.out.println(userInfo);
    }
}
