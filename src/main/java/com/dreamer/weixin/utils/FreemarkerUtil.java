package com.dreamer.weixin.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtil {
    /***
     * 获取模板信息
     * @param name 模板名字
     * @return
     */
    public Template getTemplate(String name){
        //通过freemarkerd Configuration读取相应的ftl
        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:ftl/"));
            Template template = cfg.getTemplate(name);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String print(String name, HashMap<String, Object> root) throws TemplateException, IOException {
        //通过Template可以将模板文件输出到相应的流
        StringWriter out = new StringWriter();
        Template template = this.getTemplate(name);
        template.process(root, new PrintWriter(System.out));
        out.flush();
        String context = out.toString();
        return context;
    }



}
