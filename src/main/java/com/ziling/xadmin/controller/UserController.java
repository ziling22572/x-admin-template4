package com.ziling.xadmin.controller;

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


}
