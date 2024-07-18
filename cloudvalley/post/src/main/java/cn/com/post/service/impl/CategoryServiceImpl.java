package cn.com.post.service.impl;

import cn.com.core.post.dto.CategoryQueryDTO;
import cn.com.core.post.po.Category;
import cn.com.core.util.Result;
import cn.com.post.mapper.CategoryMapper;
import cn.com.post.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 帖子分类表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public boolean add(Category category) {
        return save(category);
    }

    @Override
    public boolean deleteBatch(List<String> idList) {
        return removeBatchByIds(idList);
    }

    @Override
    public boolean update(Category category) {
        return updateById(category);
    }

    @Override
    public Category selectById(String id) {
        return getById(id);
    }

    @Override
    public IPage<Category> page(String name, Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<Category> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(!StringUtils.isEmpty(name), Category::getName, name);
        // 执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public List<Category> selectAll() {
        return list();
    }

    @Override
    public Result getCategoryList(CategoryQueryDTO queryDTO) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(queryDTO.getName()), Category::getName, queryDTO.getName());
        IPage<Category> page = page(queryDTO.getPage(), queryWrapper);
        return Result.ok(page);
    }

}
