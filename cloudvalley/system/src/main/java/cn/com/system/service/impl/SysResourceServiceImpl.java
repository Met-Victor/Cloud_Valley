package cn.com.system.service.impl;

import cn.com.core.system.po.SysResource;
import cn.com.system.mapper.SysResourceMapper;
import cn.com.system.service.ISysResourceService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-09
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SysResource saveResource(SysResource sysResource) {
        logger.debug("SysResourceServiceImpl.saveResource:{}", sysResource);
        if (StringUtils.isEmpty(sysResource.getId())) {
            if (baseMapper.exists(new LambdaQueryWrapper<SysResource>()
                    .eq(SysResource::getHash, sysResource.getHash())
                    .eq(SysResource::getName, sysResource.getName()))) {
                return null;
            }
        }
        saveOrUpdate(sysResource);
        return sysResource;

    }

    @Override
    public SysResource getByHashAndName(String hash, String name) {
        logger.debug("SysResourceServiceImpl.getByHashAndName:{},{}", hash, name);
        return getOne(new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getHash, hash)
                .eq(SysResource::getName, name)
                .last("limit 1")
        );
    }

    @Override
    public Boolean deleteResource(String id) {
        logger.debug("SysResourceServiceImpl.deleteResource:{}", id);
        SysResource resource = getById(id);
        if (ObjectUtil.isNull(resource)) {
            return false;
        }
        File file = new File(resource.getPath());
        if (file.exists()) {
            file.delete();
        }
        return removeById(id);
    }
}
