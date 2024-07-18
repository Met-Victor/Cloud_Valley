package cn.com.post.service;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子收藏表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
public interface IFavoriteService extends IService<Favorite> {

    String collect(String postId);

    Favorite getCurrentUserFavorite(String postId);

    List<UserStatusOnPostDTO> selectIsFavoritedByUserIdAndPostIds(String userId, List<String> postIdList);
}
