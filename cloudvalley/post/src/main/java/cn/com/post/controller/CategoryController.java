package cn.com.post.controller;

import cn.com.core.post.po.Category;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.ICategoryService;
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
 * 帖子分类
 *
 * @author suwenjian
 * @since 2024-03-12
 */
@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ICategoryService categoryService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid Category category) {
        logger.debug("CategoryController.add:{}", category.toString());
        boolean b = categoryService.add(category);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("CategoryController.deleteBatch:{}", idList.toString());
        boolean b = categoryService.deleteBatch(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Valid Category category) {
        logger.debug("CategoryController.update:{}", category.toString());
        boolean b = categoryService.update(category);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@NotBlank String id) {
        logger.debug("CategoryController.selectById:{}", id);
        Category category = categoryService.selectById(id);
        return category != null ? Result.ok(category) : Result.error(Constant.NOT_EXIST);
    }

    /**
     * 查询所有分类
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        logger.debug("CategoryController.selectAll");
        List<Category> list = categoryService.selectAll();
        return Result.ok(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result selectPage(@RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.ok(categoryService.page(name, pageNum, pageSize));
    }
}
