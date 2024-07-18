package cn.com.core.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author suwenjian
 * @Date 2024/3/22
 */
@Data
@ApiModel(description="帖子评论的数据传输对象")
public class CommentDTO {

    @NotBlank
    @ApiModelProperty(value = "帖子id")
    private String postId;

    @NotBlank
    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父级评论id")
    private String parentId;

    @ApiModelProperty(value = "根评论id")
    private String rootId;

    @ApiModelProperty(value = "目标用户id，取值为文章的作者id或目标评论的用户id")
    private String targetUserId;
}
