package cn.com.post.service.impl;

import cn.com.core.post.dto.PostTagNameDTO;
import cn.com.core.post.dto.TagQueryDTO;
import cn.com.core.post.po.Tag;
import cn.com.core.util.Result;
import cn.com.post.mapper.TagMapper;
import cn.com.post.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 帖子标签表 服务实现类
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public boolean add(Tag tag) {
        return save(tag);
    }

    @Override
    public boolean deleteBatch(List<String> idList) {
        return removeBatchByIds(idList);
    }

    @Override
    public boolean update(Tag tag) {
        return updateById(tag);
    }

    @Override
    public Tag selectById(String id) {
        return getById(id);
    }

    @Override
    public IPage<Tag> page(String name, Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<Tag> page = new Page<>(pageNum, pageSize);
        // 创建查询条件
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(!StringUtils.isEmpty(name), Tag::getName, name);
        // 执行分页查询
        return page(page, queryWrapper);
    }

    @Override
    public List<Tag> selectAll() {
        return list();
    }

    @Override
    public String getPostTagInfo(String postId) {
        return baseMapper.getPostTagInfo(postId);
    }

    @Override
    public List<String> getPostTagIdList(String postId) {
        return baseMapper.getPostTagIdList(postId);
    }

    @Override
    public Result getTagList(TagQueryDTO queryDTO) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(queryDTO.getName()), Tag::getName, queryDTO.getName());
        IPage<Tag> page = page(queryDTO.getPage(), queryWrapper);
        return Result.ok(page);
    }

    /**
     * 查询多个帖子的标签名
     */
    @Override
    public List<PostTagNameDTO> selectTagNameByPostIds(List<String> postIdList) {
        return baseMapper.selectTagNameByPostIds(postIdList);
    }
}
