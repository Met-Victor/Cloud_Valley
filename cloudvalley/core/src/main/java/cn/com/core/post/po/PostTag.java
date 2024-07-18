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
 * 帖子标签关联表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PostTag对象", description="帖子标签关联表")
public class PostTag extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "标签id")
    private String tagId;

}
