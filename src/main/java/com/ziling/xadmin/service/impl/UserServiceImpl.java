package com.ziling.xadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziling.xadmin.common.Constant;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.User;
import com.ziling.xadmin.mapper.UserMapper;
import com.ziling.xadmin.pojo.UserInfo;
import com.ziling.xadmin.service.UserRoleService;
import com.ziling.xadmin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziling.xadmin.utils.JwtUtil;
import com.ziling.xadmin.utils.RedisUtil;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public Map<String, Object> login(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        // 通过用户名查询用户信息
        User dbUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (dbUser == null) {
            resultMap.put("msg", "用户名或密码错误");
            resultMap.put("code", "20001");
            return resultMap;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dbUser.getId());
        userInfo.setUsername(dbUser.getUsername());
        userInfo.setPassword(dbUser.getPassword());
        // 获取用户角色信息
        List<UserRoleVO> userRoleVOS = userRoleService.listUserRole(dbUser.getId());
        userInfo.setRoles(userRoleVOS.size() > 0 ? userRoleVOS:new ArrayList<>());
        // 获取用户部门信息
        List<UserDeptVO> userDeptVOS = userRoleService.listUserDept(dbUser.getId());
        userInfo.setDepts(userDeptVOS.size() > 0? userDeptVOS:new ArrayList<>());
        // 获取菜单信息
        if (userRoleVOS.size() > 0) {
            List<Long> roleIds = userRoleVOS.stream().map(userRoleVO -> userRoleVO.getRoleId()).distinct().collect(Collectors.toList());
            List<Menu> menus = userRoleService.listMenuIdByRoleIds(roleIds);
            userInfo.setMenus(menus);
        }
        // 生成token
        String token = jwtUtil.generateToken(userInfo);
        // 将token存入redis
        redisUtil.set(Constant.LOGIN_JWT_KEY + ":userId:" + userInfo.getId(), token, 60 * 60);
        resultMap.put("token", token);
        resultMap.put("userInfo", userInfo);
        return resultMap;
    }

    @Override
    public void logout(String token) {
        // 清除redis中的token
        redisUtil.delete(Constant.LOGIN_JWT_KEY + ":userId:" + jwtUtil.getUserId(token));
    }
}
