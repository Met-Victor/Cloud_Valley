package cn.com.core.post.dto;

import cn.com.core.post.po.ForumSection;
import cn.com.core.post.po.Tag;
import cn.com.core.util.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author suwenjian
 * @Date 2024/3/28
 */
@Data
public class SectionQueryDTO extends BaseRequest<ForumSection> {

    @ApiModelProperty(value = "论坛板块名称")
    private String name;

}
