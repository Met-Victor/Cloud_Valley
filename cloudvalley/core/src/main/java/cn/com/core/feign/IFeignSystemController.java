package cn.com.core.feign;

import cn.com.core.interceptor.UserInfoFeignInterceptor;
import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.Role;
import cn.com.core.system.po.SysDict;
import cn.com.core.system.po.SysResource;
import cn.com.core.system.po.User;
import cn.com.core.system.req.UserChangeInfoReq;
import cn.com.core.system.req.UserChangePasswordReq;
import cn.com.core.system.req.UserRegisterReq;
import cn.com.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @className: IFeignSystemController
 * @author: Met.
 * @date: 2024/2/7
 **/
@FeignClient(value = "system", path = "/system", configuration = UserInfoFeignInterceptor.class)
public interface IFeignSystemController {

    /**
     * Feign 接口通过用户名查找用户信息
     *
     * @param username
     * @return {@link User}
     */
    @GetMapping("/api/feign/user/getUserByName")
    User getUserByName(@RequestParam("username") String username);

    /**
     * Feign 接口通过用户id查找用户信息
     *
     * @param userId
     * @return {@link User}
     */
    @GetMapping("/api/feign/user/getUserById")
    User getUserById(@RequestParam("userId") String userId);

    /**
     * Feign 接口资源保存
     *
     * @param sysResource
     * @return {@link SysResource}
     */
    @PostMapping("/api/feign/resource/saveResource")
    SysResource saveResource(@RequestBody SysResource sysResource);

    /**
     * Feign 接口资源保存
     *
     * @param hash
     * @param name
     * @return {@link SysResource}
     */
    @PostMapping("/api/feign/resource/getByHashAndName")
    SysResource getByHashAndName(@RequestParam String hash, @RequestParam String name);

    /**
     * Feign 资源删除
     *
     * @param id
     * @return boolean
     */
    @DeleteMapping("/api/feign/resource/deleteResource")
    boolean deleteResource(@RequestParam String id);

    /**
     * Feign 根据类型获取字典
     *
     * @return {@link List}<{@link SysDict}>
     */
    @GetMapping("/api/feign/dict/getDictByType")
    List<SysDict> getDictByType(@RequestParam String type);

    /**
     * Feign 用户注册
     *
     * @param user
     * @return boolean
     */
    @PostMapping("/api/feign/user/userRegister")
    boolean userRegister(@RequestBody User user);

    /**
     * Feign 获取用户基本信息批量 list
     *
     * @param userIdList
     * @return {@link List}<{@link UserVO}>
     */
    @GetMapping("/api/feign/user/getUserInfoList")
    List<UserVO> getUserInfoList(@RequestParam List<String> userIdList);

    /**
     * Feign 获取用户基本信息批量 map
     *
     * @param userIdList
     * @return {@link List}<{@link UserVO}>
     */
    @GetMapping("/api/feign/user/getUserInfoMap")
    Map<String, UserVO> getUserInfoMap(@RequestParam List<String> userIdList);

    /**
     * 修改用户密码
     *
     * @param userChangePasswordReq
     * @return boolean
     */
    @PostMapping("/api/feign/user/changePassword")
    boolean changePassword(@RequestBody UserChangePasswordReq userChangePasswordReq);

    /**
     * 修改用户基本信息
     *
     * @param userChangeInfoReq
     * @return boolean
     */
    @PostMapping("/api/feign/user/changeUserInfo")
    Result changeUserInfo(@RequestBody UserChangeInfoReq userChangeInfoReq);

    /**
     * 根据roleId获取对应的角色权限
     *
     * @param roleId
     * @return {@link Role}
     */
    @GetMapping("/api/feign/role/getRoleById")
    Role getRoleById(@RequestParam String roleId);

    /**
     * 获取用户列表
     *
     * @return {@link Role}
     */
    @GetMapping("/api/feign/user/getUserList")
    Result getUserList();

    /**
     * 禁用用户
     *
     * @return {@link Role}
     */
    @GetMapping("/api/feign/user/forbiddenUser")
    Result forbiddenUser(@RequestParam String userId);

    /**
     * 启用用户
     *
     * @param userId
     * @return {@link Result}
     */
    @PostMapping("/api/feign/user/startUser")
    Result startUser(@RequestParam String userId);
}
