package com.ziling.xadmin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("x_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_name")
    private String username;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("status")
    private Integer status;

    @TableField("sex")
    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "角色ID列表")
    @TableField(exist = false)
    private List<Long> roleIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<Long> deptIds;
}
