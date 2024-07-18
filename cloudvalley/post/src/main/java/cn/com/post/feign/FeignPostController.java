package cn.com.post.feign;

import cn.com.core.feign.IFeignPostController;
import cn.com.core.post.dto.CategoryQueryDTO;
import cn.com.core.post.dto.PostQueryDTO;
import cn.com.core.post.dto.SectionQueryDTO;
import cn.com.core.post.dto.TagQueryDTO;
import cn.com.core.post.po.Category;
import cn.com.core.post.po.ForumSection;
import cn.com.core.post.po.Tag;
import cn.com.core.util.Constant;
import cn.com.core.util.Result;
import cn.com.post.service.ICategoryService;
import cn.com.post.service.IForumSectionService;
import cn.com.post.service.IPostService;
import cn.com.post.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: FeignPostService
 * @author: Met.
 * @date: 2024/2/7
 **/
@RestController
public class FeignPostController implements IFeignPostController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IPostService postService;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IForumSectionService forumSectionService;

    @Resource
    private ITagService tagService;

    @Override
    public Result getPostList(PostQueryDTO postQueryDTO) {
        logger.debug("FeignPostController.getPostList:{}", postQueryDTO);
        return postService.getPostList(postQueryDTO);
    }

    @Override
    public Result addCategory(Category category) {
        logger.debug("FeignPostController.addCategory:{}", category.toString());
        boolean b = categoryService.save(category);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    @Override
    public Result deleteBatchCategory(List<String> idList) {
        logger.debug("FeignPostController.deleteBatchCategory:{}", idList.toString());
        boolean b = categoryService.removeBatchByIds(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    @Override
    public Result updateCategory(Category category) {
        logger.debug("FeignPostController.updateCategory:{}", category.toString());
        boolean b = categoryService.update(category);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    @Override
    public Result getCategoryList(CategoryQueryDTO queryDTO) {
        logger.debug("FeignPostController.getCategoryList:{}", queryDTO);
        return categoryService.getCategoryList(queryDTO);
    }

    @Override
    public Result addForumSection(ForumSection forumSection) {
        logger.debug("FeignPostController.addForumSection:{}", forumSection);
        boolean b = forumSectionService.add(forumSection);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    @Override
    public Result deleteForumSection(List<String> idList) {
        logger.debug("FeignPostController.deleteForumSection:{}", idList);
        boolean b = forumSectionService.deleteBatch(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    @Override
    public Result updateForumSection(ForumSection forumSection) {
        logger.debug("FeignPostController.updateForumSection:{}", forumSection);
        boolean b = forumSectionService.update(forumSection);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    @Override
    public Result getSectionList(SectionQueryDTO sectionQueryDTO) {
        logger.debug("FeignPostController.getSectionList:{}", sectionQueryDTO);
        return forumSectionService.getSectionList(sectionQueryDTO);
    }

    @Override
    public Result addTag(Tag tag) {
        logger.debug("FeignPostController.addTag:{}", tag);
        boolean b = tagService.add(tag);
        return b ? Result.ok() : Result.error(Constant.INSERT_FAIL);
    }

    @Override
    public Result deleteTag(List<String> idList) {
        logger.debug("FeignPostController.deleteTag:{}", idList);
        boolean b = tagService.deleteBatch(idList);
        return b ? Result.ok() : Result.error(Constant.DELETE_FAIL);
    }

    @Override
    public Result updateTag(Tag tag) {
        logger.debug("FeignPostController.updateTag:{}", tag);
        boolean b = tagService.update(tag);
        return b ? Result.ok() : Result.error(Constant.UPDATE_FAIL);
    }

    @Override
    public Result getTagList(TagQueryDTO tagQueryDTO) {
        logger.debug("FeignPostController.getTagList:{}", tagQueryDTO);
        return tagService.getTagList(tagQueryDTO);
    }

    @Override
    public Result forbiddenPost(String postId) {
        return postService.forbiddenPost(postId);
    }

    @Override
    public Result startPost(String postId) {
        return postService.startPost(postId);

    }
}
