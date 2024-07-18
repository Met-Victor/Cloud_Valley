package cn.com.core.post.dto;

import cn.com.core.post.po.Comment;
import cn.com.core.util.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author suwenjian
 * @Date 2024/3/28
 */
@Data
public class CommentQueryDTO extends BaseRequest<Comment> {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty("评论人的用户名")
    private String userName;

}
