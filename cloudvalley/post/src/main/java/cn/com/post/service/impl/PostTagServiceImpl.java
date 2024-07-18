package cn.com.post.service.impl;

import cn.com.core.post.po.PostTag;
import cn.com.post.mapper.PostTagMapper;
import cn.com.post.service.IPostTagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 帖子标签关联表 服务实现类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements IPostTagService {

    @Override
    public void add(String postId, List<String> tagIdList) {
        List<PostTag> postTagList = new ArrayList<>();
        // 构建插入数据的集合
        for (String tagId : tagIdList) {
            PostTag postTag = new PostTag();
            postTag.setPostId(postId);
            postTag.setTagId(tagId);
            postTagList.add(postTag);
        }
        // 批量插入
        saveBatch(postTagList);
    }

    @Override
    public void update(String postId, List<String> tagIdList) {
        // 帖子和标签是多对多的关系，更新帖子的标签时，需要先删除帖子原有的标签关系，然后再插入新的标签关系
        LambdaQueryWrapper<PostTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PostTag::getPostId, postId);
        this.remove(wrapper);
        add(postId, tagIdList);
    }

}
