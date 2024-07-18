package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 帖子收藏表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Favorite对象", description="帖子收藏表")
public class Favorite extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
