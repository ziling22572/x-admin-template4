package com.ziling.xadmin.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
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
@Data
@TableName("x_dept")
@ApiModel(value = "Dept对象", description = "")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("parent_id")
    private Long parentId;

    @TableField("dept_name")
    private String deptName;

    @TableField("order_num")
    private Integer orderNum;


    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_by")
    private String updateBy;

    @TableField("leader")
    private String leader;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("status")
    private Integer status;

    @TableField("ancestors")
    private String ancestors;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    @TableField(exist = false)
    private List<Dept> children;


}
