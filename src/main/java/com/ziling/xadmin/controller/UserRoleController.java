package com.ziling.xadmin.controller;

import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Role;
import com.ziling.xadmin.service.UserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @GetMapping("/{id}")
    @ApiOperation(value = "获取用户角色及菜单详情")
    private R<Role> getRoleById(@PathVariable("id") Integer id) {
        return R.data(userRoleService.getRoleById(id));
    }
}
