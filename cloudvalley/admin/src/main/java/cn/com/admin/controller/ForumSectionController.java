package cn.com.admin.controller;


import cn.com.core.feign.IFeignPostController;
import cn.com.core.post.dto.SectionQueryDTO;
import cn.com.core.post.po.ForumSection;
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
 * 后台管理-论坛板块
 *
 * @author suwenjian
 * @since 2024-03-28
 */
@RestController
@RequestMapping("/section")
@Validated
public class ForumSectionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFeignPostController feignPostController;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Valid ForumSection forumSection) {
        logger.debug("ForumSectionController.add:{}", forumSection);
        return feignPostController.addForumSection(forumSection);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public Result deleteForumSection(@RequestBody @NotEmpty List<String> idList) {
        logger.debug("ForumSectionController.deleteForumSection:{}", idList);
        return feignPostController.deleteForumSection(idList);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateForumSection(@RequestBody @Valid ForumSection forumSection) {
        logger.debug("ForumSectionController.updateForumSection:{}", forumSection);
        return feignPostController.updateForumSection(forumSection);
    }

    /**
     * 分页查询论坛板块
     */
    @PostMapping("/getSectionList")
    public Result getSectionList(@RequestBody SectionQueryDTO sectionQueryDTO) {
        logger.debug("ForumSectionController.getSectionList: {}", sectionQueryDTO);
        return feignPostController.getSectionList(sectionQueryDTO);
    }

}