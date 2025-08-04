package com.ziling.xadmin.config;

import com.ziling.xadmin.interceptor.JwtValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lingjinli
 * @date 2025年08月04日 15:39
 * @desc 拦截器配置类 拦截器配置类，用于注册自定义的JwtValidateInterceptor拦截器
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    private final JwtValidateInterceptor jwtValidateInterceptor;

    public MyInterceptorConfig(JwtValidateInterceptor jwtValidateInterceptor) {
        this.jwtValidateInterceptor = jwtValidateInterceptor;
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtValidateInterceptor)
                .addPathPatterns("/**") // 拦截路径
                // todo 放行路径
                .excludePathPatterns("/user/login",
                        "/user/logout",
                        "/user/info",
                        "/error",
                         // todo 下面是放行swagger路径
                        "/swagger**/**",
                        "/webjars/**",
                        "/v3/**",
                        "/v2/**",
                        "//doc.html"
                );

    }
}
