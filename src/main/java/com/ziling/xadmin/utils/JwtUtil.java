package com.ziling.xadmin.utils;

/**
 * @author lingjinli
 * @date 2025年06月26日 17:05
 * @desc jwt工具类
 */

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.internal.function.ParamType.JSON;

@Component
public class JwtUtil {

    //    过期小时数
    private static final long EXPIRE_TIME = 1000 * 60 * 60; // 1小时


    // 生成 JWT
    public String createToken(Object data) {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expireTime = currentTime + EXPIRE_TIME;
        // 生成 JWT
        String token = Jwts.builder().setId(UUID.randomUUID().toString()) // 设置JWT ID，可选
                .setSubject(com.alibaba.fastjson.JSON.toJSONString(data))  // 可选，设置JWT的主题
                .setIssuedAt(new Date()).setIssuer("xadmin").setIssuedAt(new Date(currentTime)).setExpiration(new Date(expireTime)).signWith(SignatureAlgorithm.HS256, encodeSecret(Constant.SECRET_KEY)).compact();
        return token;
    }

    private SecretKey encodeSecret(String secretKey) {
        byte[] encodeBytes = Base64.getEncoder().encode(secretKey.getBytes());
        return new SecretKeySpec(encodeBytes, 0, encodeBytes.length, "AES");// 使用AES加密 对称加密
    }

    //    解析 JWT
    public Claims parseToken(String token) {
        Claims body = Jwts.parser().setSigningKey(encodeSecret(Constant.SECRET_KEY)).parseClaimsJws(token).getBody();
        return body;
    }

    //    解析 JWT 为指定类型
    public <T> T parseToken(String token, Class<T> clazz) {
        Claims body = Jwts.parser().setSigningKey(encodeSecret(Constant.SECRET_KEY)).parseClaimsJws(token).getBody();
        return com.alibaba.fastjson.JSON.parseObject(body.getSubject(), clazz);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }
}

