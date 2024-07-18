package cn.com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: AuthorizationServerConfiguration
 * @author: ZZZ
 * @date: 2023/8/31
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private ClientOAuth2DataConfiguration clientOAuth2DataConfiguration;

    @Autowired
    private UserDetailsService userDetailsService;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    // 密码模式 刷新令牌
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManagerBean) //使用密码模式需要配置
                .userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                        return userDetailsService.loadUserByUsername(username);
                    }
                })
                .tokenStore(tokenStore)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); //支持GET,POST请求
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        // 将认证信息的增强器添加到端点上
        endpoints.tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单认证
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                //配置client_id
                .withClient(clientOAuth2DataConfiguration.getClientId())
                //配置client-secret
                .secret(clientOAuth2DataConfiguration.getSecret())
                //配置访问令牌的有效期
                .scopes(clientOAuth2DataConfiguration.getScopes())
                .accessTokenValiditySeconds(clientOAuth2DataConfiguration.getTokenValidityTime())
                //配置刷新token的有效期
                .refreshTokenValiditySeconds(clientOAuth2DataConfiguration.getRefreshTokenValidityTime())
                //配置grant_type，表示授权类型
                .authorizedGrantTypes(clientOAuth2DataConfiguration.getGrantTypes());
    }


}
