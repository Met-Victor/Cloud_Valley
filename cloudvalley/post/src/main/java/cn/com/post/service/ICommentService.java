package cn.com.post.service;

import cn.com.core.post.dto.CommentDTO;
import cn.com.core.post.po.Comment;
import cn.com.core.post.vo.CommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子评论表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-22
 */
public interface ICommentService extends IService<Comment> {

    boolean add(CommentDTO commentDTO);

    List<CommentVO> getPostComments(String postId);
}
