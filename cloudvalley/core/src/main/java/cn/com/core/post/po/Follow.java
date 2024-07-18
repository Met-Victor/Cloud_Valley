package cn.com.core.post.po;

import cn.com.core.util.BaseEntity;
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
 * 用户关注表
 * </p>
 *
 * @author dashengz
 * @since 2024-03-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("follow")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Follow对象", description = "用户关注表")
public class Follow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "关注者id")
    private String noticer;


}
