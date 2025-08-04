package com.ziling.xadmin.controller;

import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表")
    public R<List<Menu>> list() {
        return R.data(menuService.list());
    }

}
