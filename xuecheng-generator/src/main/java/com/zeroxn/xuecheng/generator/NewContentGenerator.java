package com.zeroxn.xuecheng.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @Author: lisang
 * @DateTime: 2023/5/10 下午7:18
 * @Description:
 */
public class NewContentGenerator {
    private static final String URL = "jdbc:mysql://10.10.10.10:3307/xuecheng_content?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String[] TABLE_NAMES = new String[]{
//			"mq_message",
//			"mq_message_history"
            "course_base",
            "course_market",
            "course_teacher",
            "course_category",
            "teachplan",
            "teachplan_media",
            "course_publish",
            "course_publish_pre"

    };
    public static void main(String[] args) {

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("lisang") // 设置作者
                            .outputDir(System.getProperty("user.dir") + "/xuecheng-generator/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.zeroxn.xuecheng") // 设置父包名
                            .moduleName("content") // 设置父包模块名
                            .serviceImpl("service.impl")
                            .xml("mapper")
                            .entity("entity.pojo");
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAMES);
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
