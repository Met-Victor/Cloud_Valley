package cn.com.system.service.impl;

import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import cn.com.core.system.req.UserChangeInfoReq;
import cn.com.core.system.req.UserChangePasswordReq;
import cn.com.core.system.req.UserRegisterReq;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.system.service.IUserService;
import cn.com.system.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表，包含用户基本信息 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-02-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean register(User user) {
        return save(user);
    }

    @Override
    public List<UserVO> getUserInfoList(List<String> userIdList) {
        return getBaseMapper().getUserInfoList(userIdList);
    }

    @Override
    public Map<String, UserVO> getUserInfoMap(List<String> userIdList) {
        List<UserVO> userInfoList = getBaseMapper().getUserInfoList(userIdList);
        Map<String, UserVO> userMap = userInfoList
                .stream()
                .collect(Collectors
                        .toMap(UserVO::getId, userVO -> userVO)
                );
        return userMap;
    }

    @Override
    public boolean changePassword(UserChangePasswordReq userChangePasswordReq) {
        User user = getById(userChangePasswordReq.getUserId());
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(userChangePasswordReq.getOldPassword())) {
            user.setPassword(userChangePasswordReq.getNewPassword());
            return updateById(user);
        } else {
            return false;
        }
    }

    @Override
    public Result changeUserInfo(UserChangeInfoReq userChangeInfoReq) {
        if (userChangeInfoReq.getEmail() != null) {
            boolean exist = getBaseMapper().exists(
                    new LambdaQueryWrapper<User>()
                            .eq(User::getEmail, userChangeInfoReq.getEmail())
            );
            if (exist) {
                return Result.error(Constant.RESULT_EMAIL_EXISTENCE);
            }
        }
        User user = getById(userChangeInfoReq.getUserId());
        if (user == null) {
            return Result.error(Constant.RESULT_USER_NO_EXISTENCE);
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(User::getId, userChangeInfoReq.getUserId())
                .set(StringUtils.isNotEmpty(userChangeInfoReq.getNickname()), User::getNickname, userChangeInfoReq.getNickname())
                .set(User::getDescription, userChangeInfoReq.getDescription())
                .set(StringUtils.isNotEmpty(userChangeInfoReq.getAvatar()), User::getAvatar, userChangeInfoReq.getAvatar())
                .set(StringUtils.isNotEmpty(userChangeInfoReq.getEmail()), User::getEmail, userChangeInfoReq.getEmail());
        boolean flag = update(updateWrapper);
        return flag ? Result.ok(Constant.RESULT_USER_CHANGE_INFO_SUCCESS) : Result.error(Constant.RESULT_USER_CHANGE_INFO_FAIL);
    }

    @Override
    public Result getUserList() {
        List<User> userList = list(new LambdaQueryWrapper<User>()
                .eq(User::getRoleId, "2"));
        return Result.ok(userList);
    }

    @Override
    public Result forbiddenUser(String userId) {
        boolean flag = update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, userId)
                .set(User::getIsAccountNoLocked, 1)
        );
        return flag ? Result.ok(Constant.RESULT_FORBIDDEN_OK) : Result.error(Constant.RESULT_FORBIDDEN_FAIL);
    }

    @Override
    public Result startUser(String userId) {
        boolean flag = update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, userId)
                .set(User::getIsAccountNoLocked, 0)
        );
        return flag ? Result.ok(Constant.RESULT_START_OK) : Result.error(Constant.RESULT_START_FAIL);
    }
}
