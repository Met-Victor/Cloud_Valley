package cn.com.post.mapper;

import cn.com.core.post.po.Comment;
import cn.com.core.post.vo.CommentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 帖子评论表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-22
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentVO> getFirstLevelComments(String postId);

    List<CommentVO> getSecondLevelComments(String rootId);
}
