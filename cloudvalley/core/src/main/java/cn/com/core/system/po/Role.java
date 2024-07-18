package cn.com.core.system.po;

import cn.com.core.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author dashengz
 * @since 2024-05-11
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("role")
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "Role对象", description = "角色表")
public class Role extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String code;


    @ApiModelProperty(value = "角色标签")
    private String label;

    @ApiModelProperty(value = "逻辑删除--0表示未删除")
    @JsonIgnore
    @TableLogic
    private Integer tag;


}
