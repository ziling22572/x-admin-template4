package com.ziling.xadmin.controller;

import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Dept;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.service.DeptService;
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
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;
    @GetMapping("/tree")
    @ApiOperation(value = "获取部门列表")
    public R<List<Dept>> treeDept() {
        return R.data(deptService.treeDept());
    }
}
