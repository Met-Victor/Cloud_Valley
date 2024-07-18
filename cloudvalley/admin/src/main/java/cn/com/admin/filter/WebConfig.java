package cn.com.admin.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @className: WebConfig
 * @author: Met.
 * @date: 2024/3/3
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private UserInfoInterceptor userInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/**");
    }


}
