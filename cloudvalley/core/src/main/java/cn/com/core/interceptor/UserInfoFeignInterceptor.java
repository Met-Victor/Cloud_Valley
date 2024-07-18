package cn.com.core.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

/**
 * @className: UserInfoFeignInterceptor
 * @author: Met.
 * @date: 2024/3/24
 **/
@Slf4j
@Configuration
public class UserInfoFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String userInfo = attributes.getRequest().getHeader("userInfo");
            if (userInfo != null) {
                template.header("userInfo", userInfo);
            }
        }
    }

    @Bean
    public Request.Options options() {
        // 连接超时设置为5秒，读取超时设置为10秒
        return new Request.Options(5L, TimeUnit.SECONDS, 10L, TimeUnit.SECONDS, true);
    }
}
