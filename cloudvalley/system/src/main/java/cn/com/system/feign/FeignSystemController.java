package cn.com.system.feign;

import cn.com.core.feign.IFeignSystemController;
import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.Role;
import cn.com.core.system.po.SysDict;
import cn.com.core.system.po.SysResource;
import cn.com.core.system.po.User;
import cn.com.core.system.req.UserChangeInfoReq;
import cn.com.core.system.req.UserChangePasswordReq;
import cn.com.core.system.req.UserRegisterReq;
import cn.com.core.util.Result;
import cn.com.system.service.IRoleService;
import cn.com.system.service.ISysDictService;
import cn.com.system.service.ISysResourceService;
import cn.com.system.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @className: FeignSystemService
 * @author: Met.
 * @date: 2024/2/7
 **/
@RestController
public class FeignSystemController implements IFeignSystemController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IUserService userService;
    @Resource
    private ISysResourceService resourceService;
    @Resource
    private ISysDictService sysDictService;

    @Resource
    private IRoleService roleService;

    @Override
    public User getUserByName(String username) {
        logger.debug("FeignSystemController.getUserByName:{}", username);
        return userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getName, username)
                .or()
                .eq(User::getEmail, username)
                .last("limit 1")
        );
    }

    @Override
    public User getUserById(String userId) {
        logger.debug("FeignSystemController.getUserById:{}", userId);
        return userService.getById(userId);
    }

    @Override
    public SysResource saveResource(SysResource sysResource) {
        logger.debug("FeignSystemController.saveResource:{}", sysResource);
        return resourceService.saveResource(sysResource);
    }

    @Override
    public SysResource getByHashAndName(String hash, String name) {
        logger.debug("FeignSystemController.getByHashAndName:{},{}", hash, name);
        return resourceService.getByHashAndName(hash, name);
    }

    @Override
    public boolean deleteResource(String id) {
        logger.debug("FeignSystemController.deleteResource:{}", id);
        return resourceService.deleteResource(id);
    }

    @Override
    public List<SysDict> getDictByType(String type) {
        logger.debug("FeignSystemController.getDictByType:{}", type);
        return sysDictService.getDictByType(type);
    }

    @Override
    public boolean userRegister(User user) {
        logger.debug("FeignSystemController.userRegister:{}", user);
        return userService.register(user);
    }

    @Override
    public List<UserVO> getUserInfoList(List<String> userIdList) {
        return userService.getUserInfoList(userIdList);
    }

    @Override
    public Map<String, UserVO> getUserInfoMap(List<String> userIdList) {
        return userService.getUserInfoMap(userIdList);

    }

    @Override
    public boolean changePassword(UserChangePasswordReq userChangePasswordReq) {
        return userService.changePassword(userChangePasswordReq);
    }

    @Override
    public Result changeUserInfo(UserChangeInfoReq userChangeInfoReq) {
        return userService.changeUserInfo(userChangeInfoReq);
    }

    @Override
    public Role getRoleById(String roleId) {
        return roleService.getById(roleId);
    }

    @Override
    public Result getUserList() {
        return userService.getUserList();
    }

    @Override
    public Result forbiddenUser(String userId) {
        return userService.forbiddenUser(userId);
    }

    @Override
    public Result startUser(String userId) {
        return userService.startUser(userId);
    }
}
