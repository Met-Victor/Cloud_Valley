package cn.com.post.service;

import cn.com.core.post.dto.SectionQueryDTO;
import cn.com.core.post.po.ForumSection;
import cn.com.core.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论坛板块表 服务类
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-14
 */
public interface IForumSectionService extends IService<ForumSection> {

    boolean add(ForumSection forumSection);

    boolean deleteBatch(List<String> idList);

    boolean update(ForumSection category);

    ForumSection selectById(String id);

    List<ForumSection> selectAll();

    Result getSectionList(SectionQueryDTO sectionQueryDTO);
}
