package cn.com.post.controller;

import cn.com.core.post.po.ForumSection;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.IForumSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 论坛板块
 *
 * @author suwenjian
 * @since 2024-03-14
 */
@RestController
@RequestMapping("/section")
@Validated
public class ForumSectionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IForumSectionService forumSectionService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid ForumSection forumSection) {
        logger.debug("ForumSectionController.add:{}", forumSection.toString());
        boolean b = forumSectionService.add(forumSection);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("ForumSectionController.deleteBatch:{}", idList.toString());
        boolean b = forumSectionService.deleteBatch(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Valid ForumSection forumSection) {
        logger.debug("ForumSectionController.update:{}", forumSection.toString());
        boolean b = forumSectionService.update(forumSection);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@NotBlank String id) {
        logger.debug("ForumSectionController.selectById:{}", id);
        ForumSection forumSection = forumSectionService.selectById(id);
        return forumSection != null ? Result.ok(forumSection) : Result.error(Constant.NOT_EXIST);
    }

    /**
     * 查询所有论坛板块
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        logger.debug("ForumSectionController.selectAll");
        List<ForumSection> list = forumSectionService.selectAll();
        return Result.ok(list);
    }

}
