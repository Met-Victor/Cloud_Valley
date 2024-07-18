package cn.com.core.post.dto;

import lombok.Data;

/**
 * 帖子和标签名的对应关系
 * @Author suwenjian
 * @Date 2024/4/11
 */
@Data
public class PostTagNameDTO {
    private String postId;
    private String tagNames;
}
