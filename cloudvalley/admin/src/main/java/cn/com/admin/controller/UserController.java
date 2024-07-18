package cn.com.admin.controller;

import cn.com.core.feign.IFeignSystemController;
import cn.com.core.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理-用户管理
 *
 * @className: UserController
 * @author: Met.
 * @date: 2024/5/11
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFeignSystemController feignSystemController;

    /**
     * 获取普通用户列表
     *
     * @return {@link Result}
     */
    @GetMapping("/getUserList")
    public Result getUserList() {
        logger.debug("UserController.getUserList");
        return feignSystemController.getUserList();
    }

    /**
     * 禁用用户
     *
     * @param userId
     * @return {@link Result}
     */
    @DeleteMapping("/forbiddenUser")
    public Result forbiddenUser(@RequestParam String userId) {
        logger.debug("UserController.forbiddenUser");
        return feignSystemController.forbiddenUser(userId);
    }

    /**
     * 启用用户
     *
     * @param userId
     * @return {@link Result}
     */
    @PostMapping("/startUser")
    public Result startUser(@RequestParam String userId) {
        logger.debug("UserController.forbiddenUser");
        return feignSystemController.startUser(userId);
    }
}
