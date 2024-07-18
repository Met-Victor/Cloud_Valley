package cn.com.core.post.dto;

import lombok.Data;

/**
 * 用户对于帖子的状态，是否点赞或是否收藏
 * @Author suwenjian
 * @Date 2024/4/11
 */
@Data
public class UserStatusOnPostDTO {
    private String postId;
    private Boolean status;
}
