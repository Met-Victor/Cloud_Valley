package cn.com.core.post.vo;

import cn.com.core.post.po.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @className: PostAdminVo
 * @author: Met.
 * @date: 2024/5/13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostAdminVO {

    @ApiModelProperty("帖子id")
    private String id;

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "帖子简介")
    private String description;

    @ApiModelProperty("帖子内容")
    private String content;

    @ApiModelProperty(value = "是否禁用")
    private Integer idLocked;

    @ApiModelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "帖子的标签名称，以 , 分割")
    private String tagNameList;

    @ApiModelProperty(value = "作者信息")
    private UserVO author;

    @ApiModelProperty(value = "帖子分类信息")
    private Category category;


}
