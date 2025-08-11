package com.ziling.xadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.mapper.MenuMapper;
import com.ziling.xadmin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> listTree(Menu menu) {
        String name = menu.getName();
        String title = menu.getTitle();
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(name)) {
            queryWrapper.like(Menu::getName, name);
        }
        if (StringUtils.hasLength(title)) {
            queryWrapper.like(Menu::getTitle, title);
        }
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        // todo 排序
        menus.sort(Comparator.comparing(Menu::getOrderNum));
        return menus;
    }



    @Override
    public List<Menu> treeMenu() {
       // todo 先通过parentId为0的菜单构建树形结构
        List<Menu> menus = baseMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, 0));
        getMenuChildren(menus);
        return menus;
    }



    private void getMenuChildren(List<Menu> menus) {
        for (Menu menu : menus) {
            menu.setChildren(Collections.emptyList());
            // todo 再通过parentId为menu.getId()的菜单构建树形结构
            List<Menu> children = baseMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, menu.getId()));
            menu.setChildren(children);
            // todo 递归构建子菜单的树形结构
            buildTree(children);
        }
    }

    private void buildTree(List<Menu> children) {
        getMenuChildren(children);
        // todo 排序
        children.sort(Comparator.comparing(Menu::getOrderNum));
    }
}
