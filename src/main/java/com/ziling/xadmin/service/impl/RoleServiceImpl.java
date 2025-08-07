package com.ziling.xadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.Role;
import com.ziling.xadmin.entity.RoleMenu;
import com.ziling.xadmin.mapper.RoleMapper;
import com.ziling.xadmin.service.MenuService;
import com.ziling.xadmin.service.RoleMenuService;
import com.ziling.xadmin.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
