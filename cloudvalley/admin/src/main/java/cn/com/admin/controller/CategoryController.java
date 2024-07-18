package cn.com.admin.controller;

import cn.com.core.feign.IFeignPostController;
import cn.com.core.post.dto.CategoryQueryDTO;
import cn.com.core.post.po.Category;
import cn.com.core.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 后台管理-帖子分类管理
 * @Author suwenjian
 * @Date 2024/3/26
 */
@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFeignPostController feignPostController;

    /**
     * 新增帖子分类
     */
    @PostMapping("/add")
    public Result addCategory(@RequestBody @Valid Category category) {
        logger.debug("CategoryAdminController.addCategory:{}", category.toString());
        return feignPostController.addCategory(category);
    }

    /**
     * 批量删除分类
     */
    @DeleteMapping("/batch")
    public Result deleteBatchCategory(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("CategoryAdminController.deleteBatchCategory:{}", idList.toString());
        return feignPostController.deleteBatchCategory(idList);
    }

    /**
     * 修改分类
     */
    @PutMapping("/update")
    public Result updateCategory(@RequestBody @Valid Category category) {
        logger.debug("CategoryAdminController.updateCategory:{}", category.toString());
        return feignPostController.updateCategory(category);
    }

    /**
     * 分页查询分类
     */
    @PostMapping("/getCategoryList")
    public Result getCategoryList(@RequestBody CategoryQueryDTO categoryQueryDTO) {
        logger.debug("CategoryAdminController.getCategoryList: {}", categoryQueryDTO);
        return feignPostController.getCategoryList(categoryQueryDTO);
    }
}
