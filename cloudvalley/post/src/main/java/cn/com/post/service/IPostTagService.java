package cn.com.post.service;

import cn.com.core.post.po.PostTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子标签关联表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
public interface IPostTagService extends IService<PostTag> {

    void add(String postId, List<String> tagIdList);

    void update(String postId, List<String> tagIdList);
}
