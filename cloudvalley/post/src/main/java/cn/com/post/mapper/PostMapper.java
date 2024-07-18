package cn.com.post.mapper;

import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.po.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-04
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    IPage<Post> userPage(IPage<Post> page, PostQueryDTO queryDTO);

    Map<String, Long> queryAuthorStatistics(String userId);

    IPage<Post> page(IPage<Post> page, PostQueryDTO queryDTO);

}
