package com.ziling.xadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.User;
import com.ziling.xadmin.pojo.UserInfo;
import com.ziling.xadmin.service.UserService;
import com.ziling.xadmin.utils.JwtUtil;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody User user) {
        // 校验用户名和密码
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.fail("用户名或密码不能为空");
        }
        Map<String, Object> resultMap = userService.login(user);
        return R.data(resultMap);
    }

    @PostMapping("/logout")
    @ApiModelProperty(value = "退出登录")
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
    @ApiModelProperty(value = "获取用户信息")
    public R info(@RequestHeader("X-Token") String token) {
        UserInfo currentUser = jwtUtil.getCurrentUser(token);
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());
        map.put("avatar", currentUser.getAvatar());
        map.put("roles", currentUser.getRoles().stream().map(UserRoleVO::getRoleName).distinct().toArray());
        map.put("depts", currentUser.getDepts().stream().map(UserDeptVO::getDeptName).distinct().toArray());
        map.put("menus", currentUser.getMenus().stream().map(Menu::getName).distinct().toArray());
        return R.data(map);
    }


    @GetMapping("/list")
    @ApiModelProperty(value = "用户列表")
    public R getUserList(@RequestParam(value = "pageNo", required = false) Long pageNo, @RequestParam(value = "pageSize", required = false) Long pageSize, User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(user.getUsername())) {
            queryWrapper.like(User::getUsername, user.getUsername());
        }
        if (StringUtils.hasLength(user.getPhone())) {
            queryWrapper.like(User::getPhone, user.getPhone());
        }
        queryWrapper.orderByDesc(User::getCreatedAt);
        Page<User> page = new Page<>(pageNo, pageSize);
        Page<User> userPage = userService.page(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return R.data(map);
    }

    @PostMapping("/add")
    @ApiModelProperty(value = "用户列表")
    public R addUser(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(user.getPhone())) {
            queryWrapper.eq(User::getPhone, user.getPhone());
        }
        // 校验用户手机号对应的用户已经存在了
        if (userService.getOne(queryWrapper) != null) {
            return R.fail("用户手机号已经存在");
        }
        userService.save(user);
        return R.success("添加用户成功");
    }


}
