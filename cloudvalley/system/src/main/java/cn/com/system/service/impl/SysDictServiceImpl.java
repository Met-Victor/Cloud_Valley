package cn.com.system.service.impl;

import cn.com.core.system.po.SysDict;
import cn.com.system.mapper.SysDictMapper;
import cn.com.system.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-10
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public List<SysDict> getDictByType(String type) {
        return list(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getType, type)
        );
    }
}
