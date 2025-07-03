package com.ziling.xadmin.config;

/**
 * @author lingjinli
 * @date 2025年06月27日 10:38
 * @desc 跨域配置类
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 允许所有来源（SpringBoot 2.4+ 推荐用这个）
        config.setAllowCredentials(true);    // 是否允许携带 Cookie
        config.addAllowedHeader("*");        // 允许所有请求头
        config.addAllowedMethod("*");        // 允许所有方法（GET、POST、PUT、DELETE...）

        // 应用于所有路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}