package com.zeroxn.xuecheng.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;

/**
 * @Author: lisang
 * @DateTime: 2023-10-24 20:02:41
 * @Description:
 */
public class LearningGenerator {
    private static final String URL = "jdbc:mysql://10.10.10.10:3307/xuecheng_learning?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String[] TABLE_NAMES = new String[]{
//			"mq_message",
//			"mq_message_history"
            "xc_choose_course",
            "xc_course_tables",
            "xc_learn_record"

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
                            .moduleName("meida") // 设置父包模块名
                            .serviceImpl("service.impl")
                            .xml("mapper")
                            .entity("entity.pojo");
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAMES)
                            .addTablePrefix("xc_");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
