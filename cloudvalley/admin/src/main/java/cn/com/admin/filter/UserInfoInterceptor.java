package cn.com.admin.filter;

import cn.com.core.util.AuthUtil;
import cn.com.core.util.JwtUser;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: UserInfoInterceptor
 * @author: Met.
 * @date: 2024/3/3
 **/
@Slf4j
@Configuration
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, @NotNull Object handler) {
        String userInfoString = request.getHeader("userInfo");
        log.debug("UserInfoInterceptor.preHandle: {}", userInfoString);
        if (StringUtils.isEmpty(userInfoString)) {
            AuthUtil.removeUser();
            return true;
        }
//         将用户信息字符串转换为 JSON 对象
        JwtUser jwtUser = JSON.parseObject(userInfoString, JwtUser.class);
        // 将用户信息放入 ThreadLocal 中
        AuthUtil.setUser(jwtUser);
        return true;
    }
}

