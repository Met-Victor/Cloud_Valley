package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 论坛板块表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ForumSection对象", description="论坛板块表")
public class ForumSection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "论坛板块名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除，0表示未删除")
    @JsonIgnore
    @TableLogic
    private Integer tag;

}
