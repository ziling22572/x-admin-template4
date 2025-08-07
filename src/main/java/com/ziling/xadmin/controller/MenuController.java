package com.ziling.xadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.*;
import com.ziling.xadmin.service.MenuService;
import com.ziling.xadmin.service.RoleMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuService roleMenuService;

    @GetMapping("/tree")
    @ApiOperation(value = "获取菜单列表")
    public R<List<Menu>> treeMenu() {
        return R.data(menuService.treeMenu());
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "获取菜单详情")
    private R<Menu> getMenuById(@PathVariable("id") Integer id){
        Menu menu = menuService.getById(id);
        return R.data(menu);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表")
    public R<List<Menu>> listMenu(Menu menu){
        return R.data(menuService.listTree(menu));
    }



    @PostMapping("/submit")
    @ApiOperation(value = "新增或更新菜单", notes = "添加新菜单或更新已有菜单信息")
    public R submit(@RequestBody Menu menu) {
        String info="菜单新增成功";
        if (menu.getId() == null) {
            // 检查菜单名称是否已存在
            LambdaQueryWrapper<Menu> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(menu.getName())) {
                queryWrapper1.eq(Menu::getName, menu.getName());
            }
            if (menuService.getOne(queryWrapper1) != null) {
                return R.fail("该菜单名称【" + menu.getName() + "】已被注册");
            }
            // 检查菜单路径是否已存在
            LambdaQueryWrapper<Menu> queryWrapper2 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(menu.getPath())) {
                queryWrapper2.eq(Menu::getPath, menu.getPath());
            }
            if (menuService.getOne(queryWrapper2) != null) {
                return R.fail("该菜单路径【" + menu.getPath() + "】已被注册");
            }
            // 自增序号
            menu.setOrderNum((int) (menuService.count() + 1));
            // 新增菜单信息
            menuService.save(menu);
        } else {
            // 更新菜单操作 (id != null)
            // 排除当前记录进行更新时的唯一性检查
            LambdaQueryWrapper<Menu> queryWrapper1 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(menu.getName())) {
                queryWrapper1.eq(Menu::getName, menu.getName());
                queryWrapper1.ne(Menu::getId, menu.getId()); // 排除当前用户
            }
            if (menuService.getOne(queryWrapper1) != null) {
                return R.fail("该菜单名称【" + menu.getName() + "】已被注册");
            }

            // 检查菜单路径是否已被其他用户使用
            LambdaQueryWrapper<Menu> queryWrapper2 = new LambdaQueryWrapper<>();
            if (StringUtils.hasLength(menu.getPath())) {
                queryWrapper2.eq(Menu::getPath, menu.getPath());
                queryWrapper2.ne(Menu::getId, menu.getId()); // 排除当前用户
            }
            if (menuService.getOne(queryWrapper2) != null) {
                return R.fail("该菜单路径【" + menu.getPath() + "】已被注册");
            }
            // 更新其他用户信息
            menuService.updateById(menu);
            info="菜单更新成功";
        }
        return R.success(info);
    }


    @DeleteMapping("/removeById")
    @ApiOperation(value = "删除用户")
    public R removeById(@RequestParam("id") Integer id) {
        // 执行删除逻辑
        if (id == null) {
            return R.fail("ID不能为空");
        }
        // 校验是否有子节点
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, id);
        if (menuService.count(queryWrapper) > 0) {
            return R.fail("该菜单下有子节点，不能删除");
        }
        // 校验是否绑定了角色菜单
        List<RoleMenu> roleMenus = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id));
        if (roleMenus.size() > 0) {
            return R.fail("该菜单已绑定角色，请先解除绑定");
        }
        boolean handleFlag = menuService.removeById(id);
        if (handleFlag) {
            return R.success("删除成功");
        }
        return R.fail("删除失败");
    }
}
