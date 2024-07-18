package cn.com.core.feign;

import cn.com.core.interceptor.UserInfoFeignInterceptor;
import cn.com.core.post.dto.CategoryQueryDTO;
import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.dto.SectionQueryDTO;
import cn.com.core.post.dto.TagQueryDTO;
import cn.com.core.post.po.Category;
import cn.com.core.post.po.ForumSection;
import cn.com.core.post.po.Tag;
import cn.com.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: IFeignPostController
 * @author: Met.
 * @date: 2024/2/7
 **/
@FeignClient(value = "post", path = "/post", configuration = UserInfoFeignInterceptor.class)
public interface IFeignPostController {

    /**
     * 获取帖子列表-根据查询条件
     *
     * @param postQueryDTO
     * @return {@link Result}
     */
    @PostMapping("/api/feign/post/getPostList")
    Result getPostList(@RequestBody PostQueryDTO postQueryDTO);

    /**
     * 新增帖子分类
     */
    @PostMapping("/api/feign/category/addCategory")
    Result addCategory(@RequestBody Category category);

    /**
     * 批量删除分类
     */
    @DeleteMapping("/api/feign/category/deleteBatchCategory")
    Result deleteBatchCategory(@RequestBody List<String> idList);

    /**
     * 修改分类名称
     */
    @PutMapping("/api/feign/category/updateCategory")
    Result updateCategory(@RequestBody Category category);

    /**
     * 分页查询分类
     */
    @PostMapping("/api/feign/category/getCategoryList")
    Result getCategoryList(@RequestBody CategoryQueryDTO categoryQueryDTO);

    /**
     * 新增论坛板块
     */
    @PostMapping("/api/feign/section/addForumSection")
    Result addForumSection(@RequestBody ForumSection forumSection);

    /**
     * 批量删除论坛板块信息
     */
    @DeleteMapping("/api/feign/section/deleteForumSection")
    Result deleteForumSection(@RequestBody List<String> idList);

    /**
     * 修改论坛板块信息
     */
    @PutMapping("/api/feign/section/updateForumSection")
    Result updateForumSection(@RequestBody ForumSection forumSection);

    /**
     * 分页查询论坛板块
     */
    @PostMapping("/api/feign/section/getSectionList")
    Result getSectionList(@RequestBody SectionQueryDTO sectionQueryDTO);

    /**
     * 新增标签
     */
    @PostMapping("/api/feign/tag/addTag")
    Result addTag(@RequestBody Tag tag);

    /**
     * 批量删除标签
     */
    @DeleteMapping("/api/feign/tag/deleteTag")
    Result deleteTag(@RequestBody List<String> idList);

    /**
     * 修改标签
     */
    @PutMapping("/api/feign/tag/updateTag")
    Result updateTag(@RequestBody Tag tag);

    /**
     * 分页查询标签
     */
    @PostMapping("/api/feign/tag/getTagList")
    Result getTagList(@RequestBody TagQueryDTO tagQueryDTO);

    /**
     * 禁用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    @DeleteMapping("/api/feign/post/forbiddenPost")
    Result forbiddenPost(@RequestParam String postId);

    /**
     * 启用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    @PostMapping("/api/feign/post/startPost")
    Result startPost(@RequestParam String postId);
}
