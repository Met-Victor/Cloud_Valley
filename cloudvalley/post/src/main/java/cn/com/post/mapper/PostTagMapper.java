package cn.com.post.mapper;

import cn.com.core.post.po.PostTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 帖子标签关联表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@Mapper
public interface PostTagMapper extends BaseMapper<PostTag> {

}
