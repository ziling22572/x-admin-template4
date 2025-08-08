package com.ziling.xadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.User;
import com.ziling.xadmin.entity.UserDept;
import com.ziling.xadmin.entity.UserRole;
import com.ziling.xadmin.pojo.UserInfo;
import com.ziling.xadmin.service.UserDeptService;
import com.ziling.xadmin.service.UserRoleService;
import com.ziling.xadmin.service.UserService;
import com.ziling.xadmin.utils.JwtUtil;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@ApiOperation(value = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserDeptService userDeptService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public R<Map<String, Object>> login(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.fail("用户名或密码不能为空");
        }
        // 校验用户名和密码
        Map<String, Object> resultMap = userService.login(user);
        if (resultMap != null&&resultMap.get("code") != null && resultMap.get("code") != "20000") {
            return R.fail((String) resultMap.get("message"));
        }
        return R.data(resultMap);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public R<Map<String, Object>> logout(@RequestHeader("X-Token") String token) {
        if (token == null) {
            return R.fail("token不能为空");
        }
        //校验token是否有效
        if (!jwtUtil.validateToken(token)) {
            return R.fail("token无效");
        }
        userService.logout(token);
        return R.success("退出登录成功");
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    public R info(@RequestHeader("X-Token") String token) {
        return R.data(  userService.getUserInfo(token));
    }


    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public R getUserList(@RequestParam(value = "pageNo", required = false) Long pageNo, @RequestParam(value = "pageSize", required = false) Long pageSize, User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(user.getUsername())) {
            queryWrapper.like(User::getUsername, user.getUsername());
        }
        if (StringUtils.hasLength(user.getPhone())) {
            queryWrapper.like(User::getPhone, user.getPhone());
        }
        if (user.getStatus() != null) {
            queryWrapper.eq(User::getStatus, user.getStatus());
        }
        queryWrapper.orderByDesc(User::getCreatedAt);
        Page<User> page = new Page<>(pageNo, pageSize);
        Page<User> userPage = userService.page(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return R.data(map);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取用户详情")
    private R<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if (user == null) {
            return R.fail("用户不存在");
        }
        // 查询用户角色
        List<Long> roleIds = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id)).stream().map(UserRole::getRoleId).collect(Collectors.toList());
        user.setRoleIds(!roleIds.isEmpty() ? roleIds : new ArrayList<>());
        return R.data(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/submit")
    @ApiOperation(value = "新增或更新用户", notes = "添加新用户或更新已有用户信息")
    public R submit(@RequestBody User user) {
        String info="用户新增成功";
        // 新用户操作 (id == null)
        if (user.getId() == null) {
            // 检查用户名是否已存在
            LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(user.getUsername())) {
                queryWrapper1.eq(User::getUsername, user.getUsername());
            }
            if (userService.getOne(queryWrapper1) != null) {
                return R.fail("该用户名【" + user.getUsername() + "】已被注册");
            }
            // 检查手机号是否已存在
            LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(user.getPhone())) {
                queryWrapper2.eq(User::getPhone, user.getPhone());
            }
            if (userService.getOne(queryWrapper2) != null) {
                return R.fail("该用户手机号【" + user.getPhone() + "】已被注册");
            }
            // 加密密码并保存新用户
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // 更新用户角色
            user.getRoleIds().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoleService.save(userRole);
            });
            userService.save(user);

        } else {
            // 更新用户操作 (id != null)
            user.setPassword(null); // 不更新密码
            // 排除当前记录进行更新时的唯一性检查
            LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(user.getUsername())) {
                queryWrapper1.eq(User::getUsername, user.getUsername());
                queryWrapper1.ne(User::getId, user.getId()); // 排除当前用户
            }
            if (userService.getOne(queryWrapper1) != null) {
                return R.fail("该用户名【" + user.getUsername() + "】已被注册");
            }

            // 检查手机号是否已被其他用户使用
            LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(user.getPhone())) {
                queryWrapper2.eq(User::getPhone, user.getPhone());
                queryWrapper2.ne(User::getId, user.getId()); // 排除当前用户
            }
            if (userService.getOne(queryWrapper2) != null) {
                return R.fail("该手机号【" + user.getPhone() + "】已被注册");
            }
            // 更新用户角色
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
            user.getRoleIds().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoleService.save(userRole);
            });
            // 更新其他用户信息
            userService.updateById(user);
            info="用户更新成功";
        }
        return R.success(info);
    }


    @DeleteMapping("/removeById")
    @ApiOperation(value = "删除用户")
    public R removeById(@RequestParam("id") Integer id) {
        // 执行删除逻辑
        if (id == null) {
            return R.fail("ID不能为空");
        }
        // 校验是否绑定了角色、部门
        LambdaQueryWrapper<UserRole> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserRole::getUserId, id);
        if (userRoleService.count(queryWrapper1) > 0) {
            return R.fail("该用户已绑定角色，请先解除绑定");
        }
        LambdaQueryWrapper<UserDept> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(UserDept::getUserId, id);
        if (userDeptService.count(queryWrapper2) > 0) {
            return R.fail("该用户已绑定部门，请先解除绑定");
        }
        boolean handleFlag = userService.removeById(id);
        return R.status(handleFlag);
    }


}
