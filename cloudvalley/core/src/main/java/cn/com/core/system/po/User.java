package cn.com.core.system.po;

import cn.com.core.util.BaseEntity;
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
 * 用户信息表，包含用户基本信息
 * </p>
 *
 * @author dashengz
 * @since 2024-02-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User对象", description = "用户信息表，包含用户基本信息")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "签名")
    private String description;

    @ApiModelProperty(value = "账户是否锁定")
    private String isAccountNoLocked;

    @ApiModelProperty(value = "逻辑删除--0表示未删除")
    @JsonIgnore
    @TableLogic
    private Integer tag;




}
