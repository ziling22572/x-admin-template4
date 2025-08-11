package com.ziling.xadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.entity.Role;
import com.ziling.xadmin.entity.RoleMenu;
import com.ziling.xadmin.entity.UserRole;
import com.ziling.xadmin.mapper.UserRoleMapper;
import com.ziling.xadmin.service.MenuService;
import com.ziling.xadmin.service.RoleMenuService;
import com.ziling.xadmin.service.RoleService;
import com.ziling.xadmin.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

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

    @Override
    public Role getRoleById(Integer id) {
        Role role = roleService.getById(id);
        List<Long> assignedMenuIds = roleMenuService.list(
                        new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id)
                ).stream()
                .map(RoleMenu::getMenuId)
                .filter(menuId -> menuId != 0)
                .collect(Collectors.toList());
        if (assignedMenuIds.isEmpty()) {
            role.setMenuIds(Collections.emptyList());
            return role;
        }
        List<Menu> allMenus = menuService.list();
        Map<Long, List<Menu>> parentIdMap = allMenus.stream()
                .collect(Collectors.groupingBy(Menu::getParentId));
        Set<Long> resultMenuIds = new HashSet<>();
        for (Long menuId : assignedMenuIds) {
            Menu menu = findMenuById(allMenus, menuId);
            if (menu != null && (menu.getParentId() != 0 || menu.getName() .equals( "首页"))) {
                resultMenuIds.add(menuId);
                collectChildMenuIds(menuId, parentIdMap, resultMenuIds);
            }
        }
        role.setMenuIds(new ArrayList<>(resultMenuIds));
        return role;
    }


    private Menu findMenuById(List<Menu> menus, Long id) {
        for (Menu m : menus) {
            if (Objects.equals(m.getId(), id)) {
                return m;
            }
        }
        return null;
    }

    private void collectChildMenuIds(Long parentId, Map<Long, List<Menu>> parentIdMap, Set<Long> result) {
        List<Menu> children = parentIdMap.getOrDefault(parentId, Collections.emptyList());
        for (Menu child : children) {
            if (result.add(child.getId())) {
                collectChildMenuIds(child.getId(), parentIdMap, result);
            }
        }
    }


    @Override
    public List<Menu> getMenusByUserId(Long userId) {
        List<Menu> parentMenus = baseMapper.listRoleIdByUserId(userId,0);
        if (parentMenus.isEmpty()) {
            return new ArrayList<>();
        }
        getChildrenMenus(userId, parentMenus);
        return parentMenus;
    }

    public void getChildrenMenus(Long userId, List<Menu> parentMenus) {
        for (Menu parent : parentMenus) {
            List<Menu> childMenus = baseMapper.listRoleIdByUserId(userId,parent.getId().intValue());
            parent.setChildren(childMenus);
            // todo 递归查询子菜单
            if (!childMenus.isEmpty()) {
                getChildrenMenus(userId, childMenus);
            }
        }
    }
}
