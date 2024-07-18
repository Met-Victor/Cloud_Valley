package cn.com.auth.controller;

import cn.com.core.auth.req.UserLoginReq;
import cn.com.core.feign.IFeignSystemController;
import cn.com.core.system.po.User;
import cn.com.core.system.req.UserChangeInfoReq;
import cn.com.core.system.req.UserChangePasswordReq;
import cn.com.core.system.req.UserRegisterReq;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 用户
 *
 * @author Met.
 * @date 2024/03/16
 */
@RequestMapping("/oauth")
@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFeignSystemController systemController;
    @Resource
    private RestTemplate restTemplate;
    // 客户端标识 ID
    @Value("${client.oauth2.client-id}")
    private String clientId;
    // 客户端安全码
    @Value("${client.oauth2.secret}")
    private String secret;
    // 授权类型
    @Value("${client.oauth2.grant_types:password}")
    private String[] grantTypes;

    /**
     * 用户登录
     *
     * @param userLoginReq
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginReq userLoginReq) {
        logger.debug("UserController.login:{}", userLoginReq);
        String tokenUrl = "http://localhost:9601/auth/oauth/token";
        // 准备请求参数，通常包括 grant_type、client_id、client_secret 和其他可能的参数
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantTypes[0]);
        map.add("client_id", clientId);
        map.add("client_secret", secret);
        map.add("username", userLoginReq.getUsername());
        map.add("password", userLoginReq.getPassword());
        // 设置 HTTP 请求头，如果需要的话
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        // 发送 POST 请求到 /oauth/token 端点
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (Exception e) {
            return Result.error("用户名或密码错误!");
        }
        JSONObject userInfo = JSON.parseObject(responseEntity.getBody()).getJSONObject("userInfo");
        if (userInfo.getString("isLocked").equals("1")) {
            return Result.error("该账号已被锁定，请联系管理员!");
        }
        // 打印响应结果
        return Result.ok(JSON.parse(responseEntity.getBody()));
    }

    /**
     * 用户注册
     *
     * @param userRegisterReq
     * @return {@link Result}
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterReq userRegisterReq) {
        logger.debug("UserController.register:{}", userRegisterReq);
        User user = systemController.getUserByName(userRegisterReq.getUsername());
        if (user != null) {
            return Result.error("用户名已存在!");
        }
        boolean flag = systemController.userRegister(userRegisterReq.covert(userRegisterReq));
        return flag ? Result.ok(Constant.RESULT_USER_REGISTER_SUCCESS) : Result.error(Constant.RESULT_USER_REGISTER_FAIL);
    }

    /**
     * 修改用户密码
     *
     * @param userChangePasswordReq
     * @return {@link Result}
     */
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody UserChangePasswordReq userChangePasswordReq) {
        logger.debug("UserController.changePassword:{}", userChangePasswordReq);
        boolean flag = systemController.changePassword(userChangePasswordReq);
        return flag ? Result.ok(Constant.RESULT_USER_CHANGE_PASSWORD_SUCCESS) : Result.error(Constant.RESULT_USER_CHANGE_PASSWORD_FAIL);
    }

    /**
     * 修改用户基本信息
     *
     * @param userChangeInfoReq
     * @return {@link Result}
     */
    @PostMapping("/changeUserInfo")
    public Result changeUserInfo(@RequestBody UserChangeInfoReq userChangeInfoReq) {
        logger.debug("UserController.changePassword:{}", userChangeInfoReq);
        return systemController.changeUserInfo(userChangeInfoReq);
    }

}
