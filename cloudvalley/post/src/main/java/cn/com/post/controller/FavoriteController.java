package cn.com.post.controller;


import cn.com.core.util.Result;
import cn.com.post.service.IFavoriteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 收藏
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@RestController
@RequestMapping("/favorite")
@Validated
public class FavoriteController {

    @Resource
    IFavoriteService favoriteService;

    /**
     * 收藏或取消收藏
     */
    @PostMapping("/collect")
    public Result collect(@NotBlank String postId) {
        return Result.ok(favoriteService.collect(postId));
    }
}
