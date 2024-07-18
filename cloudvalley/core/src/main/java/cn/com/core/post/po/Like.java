package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 帖子点赞表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Like对象", description="帖子点赞表")
@TableName("`like`")
public class Like extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "帖子id")
    private String postId;

}
