package cn.com.post.controller;


import cn.com.core.post.po.Tag;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.ITagService;
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
 * 帖子标签
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@RestController
@RequestMapping("/tag")
@Validated
public class TagController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ITagService tagService;


    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid Tag tag) {
        logger.debug("TagController.add:{}", tag.toString());
        boolean b = tagService.add(tag);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("TagController.deleteBatch:{}", idList.toString());
        boolean b = tagService.deleteBatch(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Valid Tag tag) {
        logger.debug("TagController.update:{}", tag.toString());
        boolean b = tagService.update(tag);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@NotBlank String id) {
        logger.debug("TagController.selectById:{}", id);
        Tag tag = tagService.selectById(id);
        return tag != null ? Result.ok(tag) : Result.error(Constant.NOT_EXIST);
    }

    /**
     * 查询所有标签
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        logger.debug("TagController.selectAll");
        List<Tag> list = tagService.selectAll();
        return Result.ok(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result selectPage(@RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        logger.debug("TagController.selectPage");
        return Result.ok(tagService.page(name, pageNum, pageSize));
    }

}
