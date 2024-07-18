package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 帖子评论表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comment对象", description="帖子评论表")
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论人id")
    private String userId;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父级评论id")
    private String parentId;

    @ApiModelProperty(value = "根评论id")
    private String rootId;

}
