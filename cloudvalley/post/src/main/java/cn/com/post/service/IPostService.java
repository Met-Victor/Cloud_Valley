package cn.com.post.service;

import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.po.Post;
import cn.com.core.post.vo.PostVO;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-04
 */
public interface IPostService extends IService<Post> {

    boolean add(Post post);

    boolean deleteById(String id);

    boolean deleteBatch(List<String> ids);

    boolean update(Post post);

    Result selectById(String id);

    IPage<PostVO> page(PostQueryDTO queryDTO);

    /**
     * 查询帖子 -根据postQueryDTO对象
     *
     * @param queryDTO
     * @return {@link Result}
     */
    Result getPostList(PostQueryDTO queryDTO);

    IPage<PostVO> userPage(PostQueryDTO queryDTO);

    /**
     * 禁用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    Result forbiddenPost(String postId);

    /**
     * 启用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    Result startPost(String postId);
}
