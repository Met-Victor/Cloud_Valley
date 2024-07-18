package cn.com.core.post.dto;

import cn.com.core.post.po.Post;
import cn.com.core.util.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @className: PostQueryDTO
 * @author: Met.
 * @date: 2024/3/24
 **/
@Data
@ApiModel(description="帖子的数据传输对象")
public class PostQueryDTO extends BaseRequest<Post> {
    @ApiModelProperty(value = "帖子名称")
    private String title;

    @ApiModelProperty(value = "帖子分类id")
    private String categoryId;

    @ApiModelProperty(value = "类型，取值有like、favorite、publish、recommend")
    private String type;

    @ApiModelProperty(value = "用户id", hidden = true)
    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}
