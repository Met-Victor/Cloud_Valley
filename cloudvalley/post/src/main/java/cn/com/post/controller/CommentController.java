package cn.com.post.controller;


import cn.com.core.post.dto.CommentDTO;
import cn.com.core.post.vo.CommentVO;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 评论
 * @author suwenjian
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ICommentService commentService;

    /**
     * 新增评论
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid CommentDTO commentDTO) {
        logger.debug("CommentController.add:{}", commentDTO.toString());
        boolean b = commentService.add(commentDTO);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    /**
     * 查询当前帖子下的所有评论（包括一级评论和二级评论）
     * 一级评论：直接回复帖子
     * 二级评论：回复一级或二级评论
     */
    @GetMapping("/getPostComments")
    public Result getPostComments(@NotBlank String postId) {
        logger.debug("CommentController.getPostComments:{}", postId);
        List<CommentVO> list = commentService.getPostComments(postId);
        return Result.ok(list);
    }
}
