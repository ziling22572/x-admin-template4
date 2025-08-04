package com.ziling.xadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Role;
import com.ziling.xadmin.entity.User;
import com.ziling.xadmin.entity.UserDept;
import com.ziling.xadmin.entity.UserRole;
import com.ziling.xadmin.service.RoleService;
import com.ziling.xadmin.service.UserRoleService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @GetMapping("/list")
    @ApiModelProperty(value = "角色列表")
    public R getUserList(@RequestParam(value = "pageNo", required = false) Long pageNo, @RequestParam(value = "pageSize", required = false) Long pageSize, Role role) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(role.getName())) {
            queryWrapper.like(Role::getName, role.getName());
        }
        queryWrapper.orderByDesc(Role::getCreatedAt);
        Page<Role> page = new Page<>(pageNo, pageSize);
        Page<Role> userPage = roleService.page(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return R.data(map);
    }

    @GetMapping("/{id}")
    private R<Role> getUserById(@PathVariable("id") Integer id){
        Role role = roleService.getById(id);
        return R.data(role);
    }

    @PostMapping("/submit")
    @ApiOperation(value = "新增或更新角色", notes = "添加新角色或更新已有角色信息")
    public R submit(@RequestBody Role role) {
        String info="角色新增成功";
        // 新用户操作 (id == null)
        if (role.getId() == null) {
            // 检查用户名是否已存在
            LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(role.getName())) {
                queryWrapper1.eq(Role::getName, role.getName());
            }
            if (roleService.getOne(queryWrapper1) != null) {
                return R.fail("该角色名【" + role.getName() + "】已存在");
            }

            roleService.save(role);

        } else {

            LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(role.getName())) {
                queryWrapper1.eq(Role::getName, role.getName());
                queryWrapper1.ne(Role::getId, role.getId()); // 排除当前用户
            }
            if (roleService.getOne(queryWrapper1) != null) {
                return R.fail("该角色名【" + role.getName() + "】已存在");
            }
            // 更新其他用户信息
            roleService.updateById(role);
            info="角色更新成功";
        }
        return R.success(info);
    }


    @DeleteMapping("/removeById")
    public R removeById(@RequestParam("id") Integer id) {
        // 执行删除逻辑
        if (id == null) {
            return R.fail("ID不能为空");
        }
        // 校验是否绑定了角色、部门
        LambdaQueryWrapper<UserRole> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserRole::getRoleId, id);
        if (userRoleService.count(queryWrapper1) > 0) {
            return R.fail("该角色已绑定用户，请先解除绑定");
        }
        boolean handleFlag = roleService.removeById(id);
        return R.status(handleFlag);
    }

}
