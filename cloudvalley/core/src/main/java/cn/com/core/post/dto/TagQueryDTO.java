package cn.com.core.post.dto;

import cn.com.core.post.po.Tag;
import cn.com.core.util.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author suwenjian
 * @Date 2024/3/27
 */
@Data
public class TagQueryDTO extends BaseRequest<Tag> {

    @ApiModelProperty(value = "标签名称")
    private String name;

}
