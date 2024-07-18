package cn.com.post.service;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Like;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子点赞表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
public interface ILikeService extends IService<Like> {

    String set(String postId);

    Like getCurrentUserLike(String postId);

    List<UserStatusOnPostDTO> selectIsLikedByUserIdAndPostIds(String id, List<String> postIdList);
}
