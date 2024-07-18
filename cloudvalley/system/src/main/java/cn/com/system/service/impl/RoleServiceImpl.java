package cn.com.system.service.impl;

import cn.com.core.system.po.Role;
import cn.com.system.mapper.RoleMapper;
import cn.com.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-05-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
