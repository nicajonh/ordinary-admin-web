package com.llh.server.tooltest;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * FreemarkerTest
 * <p>
 * CreatedAt: 2020-07-05 15:43
 *
 * @author llh
 */
@SpringBootTest
public class FreemarkerTest {


//    @Autowired
//    private Configuration configuration;

    @Deprecated
    public void testInit() throws IOException, URISyntaxException, TemplateException {
//        Configuration configuration = new Configuration(Configuration.getVersion());
//        ClassPathResource resource = new ClassPathResource("/freemarker/");
//        configuration.setDirectoryForTemplateLoading(resource.getFile());
//        // 设置字符集
//        configuration.setDefaultEncoding("utf-8");
        // 加载模板
//        Template template = configuration.getTemplate("demo.ftl");
//        // 数据模型
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "静态化测试");
//        // 静态化
//        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        // 打印静态化内容
//        System.out.println(content);
    }
}
