package cn.com.core.system.po;

import cn.com.core.util.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author dashengz
 * @since 2024-03-09
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("sys_resource")
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "系统资源对象", description = "资源信息表，系统资源相关信息")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID(更新时为必填参数)", position = -1)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源hash值")
    private String hash;

    @ApiModelProperty(value = "资源类型")
    private String type;

    @ApiModelProperty(value = "资源路径")
    private String path;

    @ApiModelProperty(value = "引用计数")
    private Integer reference;

    @ApiModelProperty(value = "上传者")
    private String uploader;

    @ApiModelProperty(value = "资源大小")
    private String size;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期", hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新日期", hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updateTime = new Date();


}
