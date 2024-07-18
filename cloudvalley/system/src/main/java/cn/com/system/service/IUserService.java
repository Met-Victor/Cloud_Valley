package cn.com.system.service;

import cn.com.core.post.vo.UserVO;
import cn.com.core.system.po.User;
import cn.com.core.system.req.UserChangeInfoReq;
import cn.com.core.system.req.UserChangePasswordReq;
import cn.com.core.system.req.UserRegisterReq;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表，包含用户基本信息 服务类
 * </p>
 *
 * @author dashengz
 * @since 2024-02-07
 */
public interface IUserService extends IService<User> {
    boolean register(User user);

    List<UserVO> getUserInfoList(List<String> userIdList);

    Map<String, UserVO> getUserInfoMap(List<String> userIdList);

    boolean changePassword(UserChangePasswordReq userChangePasswordReq);

    Result changeUserInfo(UserChangeInfoReq userChangeInfoReq);

    Result getUserList();

    Result forbiddenUser(String userId);

    Result startUser(String userId);
}
