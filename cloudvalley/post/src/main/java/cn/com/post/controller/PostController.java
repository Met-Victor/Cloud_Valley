package cn.com.post.controller;

import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.po.Post;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.IPostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 帖子
 *
 * @author suwenjian
 * @since 2024-03-04
 */
@RestController
@RequestMapping("/article")
@Validated
public class PostController {

    @Resource
    private IPostService postService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid Post post) {
        boolean b = postService.add(post);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody @NotEmpty List<String> ids) {
        boolean b = postService.deleteBatch(ids);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Valid Post post) {
        boolean b = postService.update(post);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@NotBlank String id) {
        return postService.selectById(id);
    }

    /**
     * 根据标题或分类分页查询文章
     */
    @PostMapping("/page")
    public Result selectPage(@RequestBody PostQueryDTO queryDTO) {
        return Result.ok(postService.page(queryDTO));
    }

    /**
     * 分页查询用户点赞、收藏或发布的文章
     */
    @PostMapping("/userPage")
    public Result userPage(@RequestBody PostQueryDTO queryDTO) {
        return Result.ok(postService.userPage(queryDTO));
    }

}
