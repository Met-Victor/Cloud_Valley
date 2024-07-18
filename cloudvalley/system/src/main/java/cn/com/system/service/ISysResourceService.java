package cn.com.system.service;

import cn.com.core.system.po.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-09
 */
public interface ISysResourceService extends IService<SysResource> {
    /**
     * 资源保存
     *
     * @param sysResource
     * @return {@link SysResource}
     */
    SysResource saveResource(SysResource sysResource);

    /**
     * 根据hash和name 来确实数据是否为唯一
     * 注：避免修改了name，没有修改内容，也无法导入
     *
     * @param hash 文件流的hash
     * @param name 文件名称
     * @return
     */
    SysResource getByHashAndName(String hash, String name);

    Boolean deleteResource(String id);

}
