package cn.com.post.service;

import cn.com.core.post.dto.CategoryQueryDTO;
import cn.com.core.post.po.Category;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子分类表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-12
 */
public interface ICategoryService extends IService<Category> {

    boolean add(Category category);

    boolean deleteBatch(List<String> idList);

    boolean update(Category category);

    Category selectById(String id);

    IPage<Category> page(String name, Integer pageNum, Integer pageSize);

    List<Category> selectAll();

    Result getCategoryList(CategoryQueryDTO queryDTO);
}
