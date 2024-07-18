package cn.com.core.post.dto;

import cn.com.core.post.po.Category;
import cn.com.core.util.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author suwenjian
 * @Date 2024/3/27
 */
@Data
public class CategoryQueryDTO extends BaseRequest<Category> {

    @ApiModelProperty(value = "分类名称")
    private String name;

}
