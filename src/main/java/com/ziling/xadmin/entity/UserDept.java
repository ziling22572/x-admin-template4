package com.ziling.xadmin.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Getter
@Setter
@TableName("x_user_dept")
@ApiModel(value = "UserDept对象", description = "")
public class UserDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("dept_id")
    private Long deptId;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}
