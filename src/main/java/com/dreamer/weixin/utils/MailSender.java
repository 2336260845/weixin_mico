package com.dreamer.weixin.utils;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.mime.MimeUtility;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;

@Slf4j
@Service
public class MailSender implements InitializingBean{

    private JavaMailSenderImpl mailSender;

    @Autowired
    FreeMarkerConfig config;

    FreemarkerUtil freemarkerUtil;

    public boolean sendWithHTMLTemplate(String to, String subject, String template, HashMap<String, Object> model){
        try {
            String nickname = MimeUtility.decodeText("Lost and Found Push Team");
            InternetAddress from = new InternetAddress(nickname + "<dreamerlijin@vip.qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      //      String result = freemarkerUtil.print(template,model);
            try{
                Template template1 = config.getConfiguration().getTemplate(template);
                try{
                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template1,model);
                    mimeMessageHelper.setTo(to);
                    mimeMessageHelper.setFrom(from);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(text,true);
                    mailSender.send(mimeMessage);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        //    String result = getContent(link);
        }catch (Exception e){
            log.error("发送邮件失败" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("dreamerlijin@vip.qq.com");
        mailSender.setPassword("sojbqnonyfsicaej");
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
