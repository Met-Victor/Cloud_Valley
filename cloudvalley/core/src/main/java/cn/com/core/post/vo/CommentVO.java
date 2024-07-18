package cn.com.core.post.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author suwenjian
 * @Date 2024/3/22
 */
@Data
public class CommentVO {

    @ApiModelProperty("评论id")
    private String id;

    @ApiModelProperty(value = "评论人id")
    private String userId;

    @ApiModelProperty("评论人的昵称")
    private String nickname;

    @ApiModelProperty("评论人的头像")
    private String avatar;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父级评论id")
    private String parentId;

    @ApiModelProperty("父评论的用户昵称")
    private String replyUser;  // 当前评论回复给 p 评论，replyUser就是 p 的评论人昵称

    @ApiModelProperty(value = "根评论id")
    private String rootId;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("二级评论的集合")
    private List<CommentVO> children; // 假如当前评论是一级评论，需要用集合保存它下面的二级评论

}
