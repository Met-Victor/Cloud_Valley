package cn.com.post.mapper;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Like;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帖子点赞表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Mapper
public interface LikeMapper extends BaseMapper<Like> {

    List<UserStatusOnPostDTO> selectIsLikedByUserIdAndPostIds(String userId, List<String> postIdList);

}
