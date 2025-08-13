package com.ziling.xadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziling.xadmin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
public interface UserService extends IService<User> {

    Map<String,Object> login(User user);

    void logout(String token);

    Map<String,Object> getUserInfo(String token);

    Page<User> pageUserInfo(Page<User> page, User user);
}
