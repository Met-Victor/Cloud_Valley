package cn.com.core.feign;

import cn.com.core.interceptor.UserInfoFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @className: IFeignAuthController
 * @author: Met.
 * @date: 2024/3/3
 **/
@FeignClient(value = "auth", path = "/auth", configuration = UserInfoFeignInterceptor.class)
public interface IFeignAuthController {

    @GetMapping("/api/feign/user/getUserByName")
    void setUser(@RequestParam String token);
}
