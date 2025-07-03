package com.ziling.xadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lingjinli
 * @date 2025年06月27日 10:11
 * @desc 功能点
 */
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // 注册 Java8 时间模块
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 不写成时间戳
        return mapper;
    }
}
