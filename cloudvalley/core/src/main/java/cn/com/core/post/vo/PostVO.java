package cn.com.core.post.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @Author suwenjian
 * @Date 2024/3/15
 */
@Data
@ApiModel(description = "帖子信息")
public class PostVO {

    @ApiModelProperty("帖子id")
    private String id;

    @ApiModelProperty("分类id")
    private String categoryId;

    @ApiModelProperty("论坛板块id")
    private String forumSectionId;

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "帖子简介")
    private String description;

    @ApiModelProperty("帖子内容")
    private String content;

    @ApiModelProperty("帖子浏览量")
    private Integer views;

    @ApiModelProperty("帖子点赞数")
    private Integer likes;

    @ApiModelProperty("帖子收藏数")
    private Integer favorites;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("帖子的标签名称，以 , 分割")
    private String tagNameList;

    @ApiModelProperty("帖子的标签id")
    private List<String> tagIdList;

    @ApiModelProperty("作者id")
    private String userId;

    @ApiModelProperty("作者昵称")
    private String nickname;

    @ApiModelProperty("作者头像")
    private String avatar;

    @ApiModelProperty("作者的文章数")
    private Long posts;

    @ApiModelProperty("作者的关注数")
    private Long follows;

    @ApiModelProperty("作者的粉丝数")
    private Long fans;

    @ApiModelProperty("当前用户是否点赞")
    private boolean isUserLike;

    @ApiModelProperty("当前用户是否收藏")
    private boolean isUserFavorite;

    @ApiModelProperty("当前用户是否关注作者")
    private boolean isUserFollow;

}
