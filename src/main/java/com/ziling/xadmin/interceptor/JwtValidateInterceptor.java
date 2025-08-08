package com.ziling.xadmin.interceptor;

import com.alibaba.fastjson.JSON;
import com.ziling.xadmin.common.Constant;
import com.ziling.xadmin.common.R;
import com.ziling.xadmin.entity.Menu;
import com.ziling.xadmin.enums.ResultCode;
import com.ziling.xadmin.pojo.UserInfo;
import com.ziling.xadmin.service.UserRoleService;
import com.ziling.xadmin.utils.JwtUtil;
import com.ziling.xadmin.utils.RedisUtil;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private  UserRoleService userRoleService;
    @Resource
    private  RedisUtil redisUtil;

    // 剩余时间小于这个值就刷新（毫秒）
    private static final long REFRESH_THRESHOLD = 10 * 60 * 1000; // 10分钟

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验token 从请求头获取
        String token =  request.getHeader("X-Token");
        if (token!= null) {
            try {
                Claims claims = jwtUtil.parseToken(token);
                log.info("校验token成功");
                // 检查剩余时间
                long remainingTime = claims.getExpiration().getTime() - System.currentTimeMillis();
                if (remainingTime < REFRESH_THRESHOLD) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(claims.get("id", Long.class));
                    userInfo.setPhone(claims.get("phone", String.class));
                    userInfo.setUsername(claims.get("username", String.class));
                    userInfo.setAvatar(claims.get("avatar", String.class));

                    List<UserRoleVO> userRoleVOS = userRoleService.listUserRole(userInfo.getId());
                    userInfo.setRoles(userRoleVOS.size() > 0 ? userRoleVOS:new ArrayList<>());
                    // 获取用户部门信息
                    List<UserDeptVO> userDeptVOS = userRoleService.listUserDept(userInfo.getId());
                    userInfo.setDepts(userDeptVOS.size() > 0? userDeptVOS:new ArrayList<>());
                    // 获取菜单信息
                    if (userRoleVOS.size() > 0) {
                        List<Long> roleIds = userRoleVOS.stream().map(userRoleVO -> userRoleVO.getRoleId()).distinct().collect(Collectors.toList());
                        List<Menu> menus = userRoleService.listMenuIdByRoleIds(roleIds);
                        userInfo.setMenus(menus);
                    }
                    // 生成token
                    String newToken = jwtUtil.createToken(userInfo);
                    // 将token存入redis
                    redisUtil.set(Constant.LOGIN_JWT_KEY + ":userId:" + userInfo.getId(), newToken,  60 * 60);
                    // 允许跨域读取自定义响应头
                    response.setHeader("Access-Control-Expose-Headers", "X-Token");
                    // 设置新的 token 到响应头
                    response.setHeader("X-Token", newToken);
                    log.info("Token 剩余时间 {} ms，小于阈值 {} ms，已刷新", remainingTime, REFRESH_THRESHOLD);
                }
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
