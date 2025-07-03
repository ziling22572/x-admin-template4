package com.ziling.xadmin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

/**
 * @author lingjinli
 * @date 2025年06月26日 15:06
 * @desc 代码生成器
 */


    public class CodeGenerator {

        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/x-admin-template?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
            String username = "root";
            String password = "123456";
            String outputDir = System.getProperty("user.dir") + "/src/main/java";
            String mapperXmlDir = System.getProperty("user.dir") + "/src/main/resources/mapper";

            FastAutoGenerator.create(url, username, password)
                    // 全局配置
                    .globalConfig(builder -> {
                        builder.author("ziling")              // 设置作者
                                .outputDir(outputDir)               // 指定输出目录
                                .enableSwagger()                    // 开启 swagger 注解
                                .commentDate("yyyy-MM-dd")          // 注释日期格式
                                .dateType(DateType.TIME_PACK);      // 时间策略: java.time包
                    })

                    // 包配置
                    .packageConfig(builder -> {
                        builder.parent("com.ziling.xadmin")            // 包名前缀
//                                .moduleName("system")                // 模块名（可为空）
                                .entity("entity")
                                .service("service")
                                .serviceImpl("service.impl")
                                .mapper("mapper")
                                .xml("mapper.xml")
                                .controller("controller")
                                .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXmlDir)); // XML路径
                    })

                    // 策略配置
                    .strategyConfig(builder -> {
                        builder
                                .addInclude("x_user", "x_role", "x_dept", "x_menu", "x_user_role", "x_role_menu", "x_user_dept")          // 指定要生成的表名，多个用逗号分隔
                                .addTablePrefix("x_")        // 过滤表前缀
                                // Entity 策略
                                .entityBuilder()
                                .naming(NamingStrategy.underline_to_camel)
                                .columnNaming(NamingStrategy.underline_to_camel)
                                .enableLombok()
                                .idType(IdType.AUTO)
                                .enableTableFieldAnnotation()

                                // Mapper 策略
                                .mapperBuilder()
                                .enableMapperAnnotation()
                                .enableBaseResultMap()
                                .enableBaseColumnList()

                                // Service 策略
                                .serviceBuilder()
                                .formatServiceFileName("%sService")
                                .formatServiceImplFileName("%sServiceImpl")

                                // Controller 策略
                                .controllerBuilder()
                                .enableRestStyle()
                                .formatFileName("%sController");
                    })

                    // 模板配置（可选）
//                    .templateConfig(builder -> {
//                        builder.controller("/templates/controller.java");
//                        // 如果你使用默认模板，可省略此配置
//                    })

                    // 执行生成器
                    .execute();
        }
    }



