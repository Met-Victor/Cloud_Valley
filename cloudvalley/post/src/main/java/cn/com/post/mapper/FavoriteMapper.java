package cn.com.post.mapper;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 帖子收藏表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

    List<UserStatusOnPostDTO> selectIsFavoritedByUserIdAndPostIds(String userId, List<String> postIdList);

}
