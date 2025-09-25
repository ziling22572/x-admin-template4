package com.ziling.xadmin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingjinli
 * @date 2025年08月19日 10:52
 * @desc 通用响应数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseData {
    private String code="200";
    private String message="success";
    private Object data;
    private Object end=null;
    private Object page=null;
    private Object pageSize=null;
    private Object param=null;
    private Object resultsCount=null;
    private Object rowCount=null;
    private Object rows=null;
    private Object sql=null;
    private Object start=null;
}
