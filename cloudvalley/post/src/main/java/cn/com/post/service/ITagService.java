package cn.com.post.service;

import cn.com.core.post.dto.PostTagNameDTO;
import cn.com.core.post.dto.TagQueryDTO;
import cn.com.core.post.po.Tag;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子标签表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
public interface ITagService extends IService<Tag> {

    boolean add(Tag tag);

    boolean deleteBatch(List<String> idList);

    boolean update(Tag tag);

    Tag selectById(String id);

    IPage<Tag> page(String name, Integer pageNum, Integer pageSize);

    List<Tag> selectAll();

    String getPostTagInfo(String postId);

    List<String> getPostTagIdList(String postId);


    Result getTagList(TagQueryDTO tagQueryDTO);

    List<PostTagNameDTO> selectTagNameByPostIds(List<String> postIdList);
}
