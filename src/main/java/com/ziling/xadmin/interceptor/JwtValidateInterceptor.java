package com.ziling.xadmin.interceptor;

import com.alibaba.fastjson.JSON;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.enums.ResultCode;
import com.ziling.xadmin.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingjinli
 * @date 2025年08月04日 15:29
 * @desc Jwt校验拦截器
 */
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    public JwtValidateInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验token 从请求头获取
        String token =  request.getHeader("X-Token");
        if (token!= null) {
            try {
                jwtUtil.parseToken(token);
                log.info("校验token成功");
                return true;
            } catch (Exception e) {
                log.error("校验token失败");
                e.printStackTrace();
            }
        }
        // 返回给前端提示
        response.setContentType("application/json;charset=UTF-8");
        R result = R.fail(ResultCode.JWT_ERROR);
        response.getWriter().write(JSON.toJSONString(result));
        return false;
    }
}
