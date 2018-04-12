package com.tiantian.freemark;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 戴礼明
 * @create 2018/4/12 10:05
 */
public class FreemarkerTest {
    @Test
    public void testFreemarkerFirst() throws Exception {
        // 创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File("F:/tiantian-parent/tiantian-item-web/src/main/resources/ftl"));
        // 设置模板字符集
        configuration.setDefaultEncoding("UTF-8");
        // 加载模板文件
        Template template = configuration.getTemplate("hello.ftl");
        // 创建一个数据集
        Map data = new HashMap();
        data.put("hello", "Hello Freemarker!!!");
        // 设置模板输出的目录及输出的文件名
        FileWriter writer = new FileWriter(new File("F:/hello.html"));
        // 生成文件
        template.process(data, writer);
        // 关闭流
        writer.close();
    }
}
