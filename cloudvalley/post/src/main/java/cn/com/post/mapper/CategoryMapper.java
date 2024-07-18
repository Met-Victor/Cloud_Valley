package cn.com.post.mapper;

import cn.com.core.post.po.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 帖子分类表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-12
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
