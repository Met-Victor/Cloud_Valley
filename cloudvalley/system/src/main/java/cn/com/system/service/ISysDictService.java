package cn.com.system.service;

import cn.com.core.system.po.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author dashengz
 * @since 2024-03-10
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 获取字典-根据类型
     *
     * @param type
     * @return {@link SysDict}
     */
    List<SysDict> getDictByType(String type);

}
