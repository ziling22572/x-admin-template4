package com.ziling.xadmin.service.impl;

import com.ziling.xadmin.entity.Dept;
import com.ziling.xadmin.mapper.DeptMapper;
import com.ziling.xadmin.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> treeDept() {
        List<Dept> deptList = baseMapper.listDeptByParentId(0L);
        deptList.forEach(this::buildTree);
        return deptList;
    }

    private void buildTree(Dept dept) {
        List<Dept> children = baseMapper.listDeptByParentId(dept.getId());
        dept.setChildren(children);
        children.forEach(this::buildTree);
    }
}
