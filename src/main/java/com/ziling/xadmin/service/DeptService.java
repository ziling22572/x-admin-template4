package com.ziling.xadmin.service;

import com.ziling.xadmin.entity.Dept;
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
public interface DeptService extends IService<Dept> {

    List<Dept> treeDept();
}
