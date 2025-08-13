package com.ziling.xadmin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziling.xadmin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> pageUserInfo(Page<User> page, @Param("user") User user);
}
