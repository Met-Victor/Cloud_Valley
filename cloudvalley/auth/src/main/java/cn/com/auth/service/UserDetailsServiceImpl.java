package cn.com.auth.service;

import cn.com.core.system.po.Role;
import cn.com.core.system.po.User;
import cn.com.core.feign.IFeignSystemController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @className: UserService
 * @author: ZZZ
 * @date: 2023/8/31
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFeignSystemController feignSystemController;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("数据：{}", username);
        User user = feignSystemController.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("no user");
        } else {
            logger.debug("user info：{}", user);
            Role role = feignSystemController.getRoleById(user.getRoleId());
            logger.debug("role info：{}", role);
            JwtUser jwtUser = new JwtUser(
                    user.getId(), user.getName(), user.getPassword(),
                    user.getEmail(), user.getAvatar(), user.getDescription(),
                    user.getNickname(), role, user.getIsAccountNoLocked()
            );
            return jwtUser;
        }
    }
}

