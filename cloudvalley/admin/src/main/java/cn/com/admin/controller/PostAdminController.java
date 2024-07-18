package cn.com.admin.controller;

import cn.com.core.feign.IFeignPostController;
import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理-帖子管理
 *
 * @author Met.
 * @date 2024/03/24
 */
@RestController
@RequestMapping("/post")
public class PostAdminController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFeignPostController feignPostController;

    /**
     * 获取帖子列表(按照查询条件)
     *
     * @param postQueryDTO
     * @return {@link Result}
     */
    @PostMapping("/getPostList")
    public Result getPostList(@RequestBody PostQueryDTO postQueryDTO) {
        logger.debug("PostAdminController.getPostList: {}", postQueryDTO);
        return feignPostController.getPostList(postQueryDTO);
    }


    /**
     * 禁用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    @DeleteMapping("/forbiddenPost")
    public Result forbiddenPost(@RequestParam String postId) {
        logger.debug("PostAdminController.forbiddenPost: {}", postId);
        return feignPostController.forbiddenPost(postId);
    }

    /**
     * 启用帖子
     *
     * @param postId
     * @return {@link Result}
     */
    @PostMapping("/startPost")
    public Result startPost(@RequestParam String postId) {
        logger.debug("PostAdminController.startPost: {}", postId);
        return feignPostController.startPost(postId);
    }


}
