package cn.com.admin.controller;

import cn.com.core.feign.IFeignPostController;
import cn.com.core.post.dto.TagQueryDTO;
import cn.com.core.post.po.Tag;
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
 * 后台管理-帖子标签管理
 *
 * @author suwenjian
 * @since 2024-03-27
 */
@RestController
@RequestMapping("/tag")
@Validated
public class TagController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFeignPostController feignPostController;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result addTag(@RequestBody @Valid Tag tag) {
        logger.debug("TagController.addTag:{}", tag);
        return feignPostController.addTag(tag);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteTag(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("TagController.deleteTag:{}", idList);
        return feignPostController.deleteTag(idList);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateTag(@RequestBody @Valid Tag tag) {
        logger.debug("TagController.updateTag:{}", tag);
        return feignPostController.updateTag(tag);
    }

    /**
     * 分页查询标签
     */
    @PostMapping("/getTagList")
    public Result getTagList(@RequestBody TagQueryDTO tagQueryDTO) {
        logger.debug("TagController.getTagList: {}", tagQueryDTO);
        return feignPostController.getTagList(tagQueryDTO);
    }

}
