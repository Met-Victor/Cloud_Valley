package cn.com.post.service.impl;

import cn.com.core.post.dto.SectionQueryDTO;
import cn.com.core.post.po.ForumSection;
import cn.com.core.util.Result;
import cn.com.post.mapper.ForumSectionMapper;
import cn.com.post.service.IForumSectionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 论坛板块表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-14
 */
@Service
public class ForumSectionServiceImpl extends ServiceImpl<ForumSectionMapper, ForumSection> implements IForumSectionService {

    @Override
    public boolean add(ForumSection forumSection) {
        return save(forumSection);
    }

    @Override
    public boolean deleteBatch(List<String> idList) {
        return removeBatchByIds(idList);
    }

    @Override
    public boolean update(ForumSection category) {
        return updateById(category);
    }

    @Override
    public ForumSection selectById(String id) {
        return getById(id);
    }

    @Override
    public List<ForumSection> selectAll() {
        return list();
    }

    @Override
    public Result getSectionList(SectionQueryDTO queryDTO) {
        LambdaQueryWrapper<ForumSection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(queryDTO.getName()), ForumSection::getName, queryDTO.getName());
        IPage<ForumSection> page = page(queryDTO.getPage(), queryWrapper);
        return Result.ok(page);
    }
}
