package com.ziling.xadmin.service;

import com.ziling.xadmin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ziling
 * @since 2025-06-26
 */
public interface MenuService extends IService<Menu> {

    List<Menu> treeMenu();

    List<Menu> listTree(Menu menu);
}
