package cn.com.post.mapper;

import cn.com.core.post.dto.PostTagNameDTO;
import cn.com.core.post.po.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 帖子标签表 Mapper 接口
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    String getPostTagInfo(String postId);

    List<PostTagNameDTO> selectTagNameByPostIds(List<String> postIdList);

    List<String> getPostTagIdList(String postId);
}
