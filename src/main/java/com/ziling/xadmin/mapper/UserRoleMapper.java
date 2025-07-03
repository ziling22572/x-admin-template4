package com.ziling.xadmin.mapper;

import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRoleVO> selectUserRole(@Param(value = "userId") Long userId);

    List<UserDeptVO> selectUserDept(@Param(value = "userId") Long userId);

    List<Menu> listMenuIdByRoleIds(@Param(value = "roleIds")  List<Long> roleIds);
}
