package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 帖子表
 * </p>
 *
 * @author suwenjian
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Post对象", description = "帖子表")
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "分类id")
    @NotBlank
    private String categoryId;

    @ApiModelProperty(value = "论坛板块id")
    private String forumSectionId;

    @ApiModelProperty(value = "帖子简介")
    @NotBlank
    private String description;

    @ApiModelProperty(value = "帖子标题")
    @NotBlank
    private String title;

    @NotBlank
    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "帖子内容")
    @NotBlank
    private String content;

    @ApiModelProperty(value = "帖子浏览量")
    private Integer views;

    @ApiModelProperty(value = "帖子点赞数")
    private Integer likes;

    @ApiModelProperty(value = "帖子收藏数")
    private Integer favorites;

    @ApiModelProperty(value = "是否锁定（下架）1--下架  ")
    private Integer isLock;

    @ApiModelProperty(value = "逻辑删除--0表示未删除")
    @JsonIgnore
    @TableLogic
    private Integer tag;

    @ApiModelProperty(value = "帖子的标签id")
    @TableField(exist = false)
    private List<String> tagIdList;
}
