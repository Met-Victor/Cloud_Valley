package cn.com.post.service.impl;

import cn.com.core.post.dto.UserStatusOnPostDTO;
import cn.com.core.post.po.Favorite;
import cn.com.core.post.po.Post;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.post.mapper.FavoriteMapper;
import cn.com.post.mapper.PostMapper;
import cn.com.post.service.IFavoriteService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子收藏表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

    @Resource
    PostMapper postMapper;

    /**
     * 收藏或取消收藏
     */
    @Override
    public String collect(String postId) {
        String res;
        // 查询用户的收藏记录
        Favorite dbRecord = getCurrentUserFavorite(postId);
        LambdaUpdateWrapper<Post> postUpdateWrapper = new LambdaUpdateWrapper<>();
        postUpdateWrapper.eq(Post::getId, postId);
        // 根据是否有收藏记录进行收藏或取消收藏
        if (dbRecord == null) {
            Favorite favorite = new Favorite();
            favorite.setUserId(AuthUtil.getUserInfo().getId());
            favorite.setPostId(postId);
            save(favorite);
            postUpdateWrapper.setSql("favorites = favorites + 1");
            res = Constant.FAVORITE_SUCCESS;
        } else {
            removeById(dbRecord.getId());
            postUpdateWrapper.setSql("favorites = favorites - 1");
            res = Constant.CANCEL_FAVORITE_SUCCESS;
        }
        // 更新文章收藏数
        postMapper.update(null, postUpdateWrapper);
        return res;
    }

    @Override
    public Favorite getCurrentUserFavorite(String postId) {
        String userId = AuthUtil.getUserInfo().getId();
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId).eq(Favorite::getPostId, postId);
        return getOne(queryWrapper);
    }

    /**
     * 查询某用户对多个帖子的收藏情况
     */
    @Override
    public List<UserStatusOnPostDTO> selectIsFavoritedByUserIdAndPostIds(String userId, List<String> postIdList) {
        return this.baseMapper.selectIsFavoritedByUserIdAndPostIds(userId, postIdList);
    }

}
