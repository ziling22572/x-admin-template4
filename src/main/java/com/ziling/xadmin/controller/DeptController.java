package com.ziling.xadmin.controller;

import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Dept;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.pojo.CommonResponseData;
import com.ziling.xadmin.service.DeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    // todo 提供一个application/x-www-form-urlencoded的post请求接口，用于添加部门信息,返回一个json格式的string字符串
//    @PostMapping(value = "/add", consumes = "application/x-www-form-urlencoded")
//    @ApiOperation(value = "添加部门")
//    public String addDept(@RequestParam Map<String, String> params) {
//        // 这里 params 里可能包含一个 "params" 字段，其值是 JSON 字符串
//        String jsonStr = params.get("params");
//        System.out.println("收到 form-urlencoded: " + jsonStr);
//        Dept dept = com.alibaba.fastjson.JSON.parseObject(jsonStr, Dept.class);
//        deptService.save(dept);
//        // 把新增的部门信息返回给前端：json字符串形式
//        return com.alibaba.fastjson.JSON.toJSONString(dept);
//    }


    @PostMapping(value = "/add", consumes = "application/x-www-form-urlencoded")
    @ApiOperation(value = "添加部门")
    public R<CommonResponseData> addDept(@RequestParam Map<String, String> params) {
        // mock CommonResponseData的data,封装一个部门的list集合
        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setData(deptService.treeDept());
        return R.data(commonResponseData);
    }

}
