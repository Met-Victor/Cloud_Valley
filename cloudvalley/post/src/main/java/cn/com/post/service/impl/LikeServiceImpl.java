package cn.com.post.service.impl;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Like;
import cn.com.core.post.po.Post;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.post.mapper.LikeMapper;
import cn.com.post.mapper.PostMapper;
import cn.com.post.service.ILikeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子点赞表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Service
@Slf4j
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {

    @Resource
    PostMapper postMapper;

    /**
     * 点赞或取消点赞
     */
    @Override
    public String set(String postId) {
        String res;
        // 查询当前用户的点赞记录
        Like dbRecord = getCurrentUserLike(postId);
        // 根据是否有点赞记录进行点赞或取消点赞
        LambdaUpdateWrapper<Post> postUpdateWrapper = new LambdaUpdateWrapper<>();
        postUpdateWrapper.eq(Post::getId, postId);
        if (dbRecord == null) {
            Like like = new Like();
            like.setUserId(AuthUtil.getUserInfo().getId());
            like.setPostId(postId);
            save(like);
            postUpdateWrapper.setSql("likes = likes + 1");
            res = Constant.LIKE_SUCCESS;
        } else {
            removeById(dbRecord.getId());
            postUpdateWrapper.setSql("likes = likes - 1");
            res = Constant.CANCEL_LIKE_SUCCESS;
        }
        // 更新文章点赞数
        postMapper.update(null, postUpdateWrapper);
        return res;
    }

    /**
     * 查询当前用户的点赞记录
     */
    @Override
    public Like getCurrentUserLike(String postId) {
        String userId = AuthUtil.getUserInfo().getId();
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getUserId, userId).eq(Like::getPostId, postId);
        return getOne(queryWrapper);
    }

    /**
     * 查询某用户对多个帖子的点赞情况
     */
    @Override
    public List<UserStatusOnPostDTO> selectIsLikedByUserIdAndPostIds(String userId, List<String> postIdList) {
        return this.baseMapper.selectIsLikedByUserIdAndPostIds(userId, postIdList);
    }

    

}
