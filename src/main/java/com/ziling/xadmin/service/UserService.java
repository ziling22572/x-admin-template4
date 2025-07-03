package com.ziling.xadmin.service;

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
}
