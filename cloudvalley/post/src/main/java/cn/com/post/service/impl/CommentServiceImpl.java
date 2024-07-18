package cn.com.post.service.impl;

import cn.com.core.post.dto.CommentDTO;
import cn.com.core.post.po.Comment;
import cn.com.core.post.po.Message;
import cn.com.core.post.vo.CommentVO;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.Constant;
import cn.com.post.mapper.CommentMapper;
import cn.com.post.service.ICommentService;
import cn.com.post.util.rabbitmqutil.SendMessageUtil;
import cn.com.post.util.wordutil.SensitiveWordUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子评论表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    CommentMapper commentMapper;

    @Resource
    SensitiveWordUtil sensitiveWordUtil;

    @Resource
    SendMessageUtil sendMessageUtil;

    @Override
    public boolean add(CommentDTO commentDTO) {
        // commentDTO 转换为 comment 对象
        Comment comment = BeanUtil.copyProperties(commentDTO, Comment.class);
        String currentUserId = AuthUtil.getUserInfo().getId();
        comment.setUserId(currentUserId);
        boolean flag = commentDTO.getParentId() == null;
        sendMessageUtil.sendCommentMessage(Message
                .builder()
                .senderId(currentUserId)
                .receiverId(commentDTO.getTargetUserId())
                .type(flag ? Constant.DICT_MESSAGE_TYPE_COMMENT : Constant.DICT_MESSAGE_TYPE_COMMENT_REPLY)
                .content(flag ? Constant.MESSAGE_CONTENT_COMMENT : Constant.MESSAGE_CONTENT_COMMENT_REPLY)
                .build());
        return save(comment);
    }

    @Override
    public List<CommentVO> getPostComments(String postId) {
        // 查询一级评论
        List<CommentVO> commentList = commentMapper.getFirstLevelComments(postId); // 一级评论的 parentId 字段值为空
        // 查询二级评论
        for (CommentVO comment : commentList) {
            // 一级评论敏感词处理
            comment.setContent(sensitiveWordUtil.replace(comment.getContent()));
            // 查询每一个一级评论下的二级评论列表
            List<CommentVO> children = commentMapper.getSecondLevelComments(comment.getId()); // 二级评论 rootId 字段值等于当前一级评论的id
            // 二级评论敏感词处理
            children.forEach(child -> child.setContent(sensitiveWordUtil.replace(child.getContent())));
            comment.setChildren(children);
        }
        return commentList;
    }

}
