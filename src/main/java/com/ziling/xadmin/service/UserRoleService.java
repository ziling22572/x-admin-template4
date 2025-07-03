package com.ziling.xadmin.service;

import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
public interface UserRoleService extends IService<UserRole> {

    List<UserRoleVO> listUserRole(Long id);

    List<UserDeptVO> listUserDept(Long id);

    List<Menu> listMenuIdByRoleIds(List<Long> roleIds);
}
