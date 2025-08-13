package com.ziling.xadmin.mapper;

import com.ziling.xadmin.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DeptMapper extends BaseMapper<Dept> {
    /**
     * 根据父部门ID查询子部门列表
     * @param parentId 父部门ID
     * @return 子部门列表
     */
    List<Dept> listDeptByParentId(@Param("parentId") Long parentId);
}
