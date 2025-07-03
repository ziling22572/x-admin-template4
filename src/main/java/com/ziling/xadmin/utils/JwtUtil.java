package com.ziling.xadmin.utils;

/**
 * @author lingjinli
 * @date 2025年06月26日 17:05
 * @desc jwt工具类
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziling.xadmin.common.Constant;
import com.ziling.xadmin.entity.*;
import com.ziling.xadmin.pojo.UserInfo;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

//    过期小时数
    private static final long EXPIRE_TIME = 1000 * 60 * 60; // 1小时

    // 生成 token
    public String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userInfo.getId());
        claims.put("username", userInfo.getUsername());
        claims.put("phone", userInfo.getPhone());
        claims.put("roles", userInfo.getRoles());
        claims.put("depts", userInfo.getDepts());
        claims.put("menus", userInfo.getMenus());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userInfo.getUsername())  // 可选
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, Constant.SECRET_KEY)
                .compact();
    }


    public UserInfo getCurrentUser(String token) {
        Claims claims = getClaims(token);
        Long userId = claims.get("userId", Integer.class).longValue(); // JJWT 可能解析为 Integer
        String username = claims.get("username", String.class);
        String phone = claims.get("phone", String.class);
        List<Map<String, Object>> rawList = claims.get("roles", List.class);
       // 然后转成你想要的类型
        ObjectMapper mapper = new ObjectMapper();
        List<UserRoleVO> roles = rawList.stream()
                .map(map -> mapper.convertValue(map, UserRoleVO.class))
                .collect(Collectors.toList());

        List<Map<String, Object>>  deptsList= claims.get("depts", List.class);
        List<UserDeptVO> depts = deptsList.stream()
               .map(map -> mapper.convertValue(map, UserDeptVO.class))
               .collect(Collectors.toList());

        List<Map<String, Object>> menusList = claims.get("menus", List.class);
        List<Menu> menus = menusList.stream()
                .map(map -> mapper.convertValue(map, Menu.class))
                .collect(Collectors.toList());
        return new UserInfo(userId, username, phone, roles, depts, menus);

    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Constant.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 提供快速获取某些字段的方法
    public String getUsername(String token) {
        return getClaims(token).get("username", String.class);
    }

    public String getPhone(String token) {
        return getClaims(token).get("phone", String.class);
    }

    public Long getUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }
}

