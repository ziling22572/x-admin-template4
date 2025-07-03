package com.ziling.xadmin.service.impl;

import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.UserRole;
import com.ziling.xadmin.mapper.UserRoleMapper;
import com.ziling.xadmin.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRoleVO> listUserRole(Long id) {
        return baseMapper.selectUserRole(id);
    }

    @Override
    public List<UserDeptVO> listUserDept(Long id) {
        return baseMapper.selectUserDept(id);
    }

    @Override
    public List<Menu> listMenuIdByRoleIds(List<Long> roleIds) {
        return baseMapper.listMenuIdByRoleIds(roleIds);
    }
}
