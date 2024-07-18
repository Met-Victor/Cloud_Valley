package cn.com.post.controller;


import cn.com.core.util.Result;
import cn.com.post.service.ILikeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 点赞
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@RestController
@RequestMapping("/like")
@Validated
public class LikeController {

    @Resource
    ILikeService likeService;

    /**
     * 点赞或取消点赞
     */
    @PostMapping("/set")
    public Result set(@NotBlank String postId) {
        return Result.ok(likeService.set(postId));
    }

}
