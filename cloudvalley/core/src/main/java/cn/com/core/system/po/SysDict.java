package cn.com.core.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author dashengz
 * @since 2024-03-10
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value="SysDict对象", description="系统资源表")
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "资源类型")
    private String label;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
