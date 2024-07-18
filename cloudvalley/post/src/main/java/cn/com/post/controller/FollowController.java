package cn.com.post.controller;


import cn.com.core.util.Result;
import cn.com.post.service.IFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 用户关注
 *
 * @author dashengz
 * @since 2024-03-11
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFollowService followService;

    /**
     * 添加关注
     *
     * @return {@link Result}
     */
    @PostMapping("/addFollow")
    public Result addFollow(@RequestParam @NotBlank String noticerId) {
        logger.debug("FollowController.addFollow:{}", noticerId);
        return followService.addFollow(noticerId);
    }

    /**
     * 取消关注
     *
     * @param noticerId
     * @return {@link Result}
     */
    @DeleteMapping("/cancelFollow")
    public Result cancelFollow(@RequestParam @NotBlank String noticerId) {
        logger.debug("FollowController.addFollow:{}", noticerId);
        return followService.cancelFollow(noticerId);
    }

    /**
     * 获取登录用户关注列表
     *
     * @return {@link Result}
     */
    @GetMapping("/getFollowList")
    public Result getFollowList() {
        logger.debug("FollowController.getFollowList:");
        return followService.getFollowList();
    }
}
