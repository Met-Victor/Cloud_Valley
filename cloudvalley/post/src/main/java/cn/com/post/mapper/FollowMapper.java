package cn.com.post.mapper;

import cn.com.core.post.po.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

/**
 * <p>
 * 用户关注表 Mapper 接口
 * </p>
 *
 * @author dashengz
 * @since 2024-03-11
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

}
