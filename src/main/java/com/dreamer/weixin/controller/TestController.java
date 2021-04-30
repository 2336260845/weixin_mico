package com.dreamer.weixin.controller;

import com.dreamer.weixin.aop.CodeCheck;
import com.dreamer.weixin.utils.DateUtil;
import com.dreamer.weixin.utils.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Date;
import java.util.HashMap;

@Controller
@Slf4j
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    MailSender mailSender;


    @RequestMapping(value = "sendMessage",method = RequestMethod.GET)
    public void sendMailMessage(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("lost_card","1609104033");
        map.put("finder_name","李进");
        map.put("finder_phone","18307972092");
        map.put("finder_email","526302433@qq.com");
        map.put("find_time", DateUtil.formatDate(new Date()));
        mailSender.sendWithHTMLTemplate("2469697621@qq.com","喜大奔波，您的一卡通已找到啦！","CardEmailTemplate.ftl",map);
    }

    @CodeCheck
    @RequestMapping("aop")
    @ResponseBody
    public String callSomeInterface() {
        return "调用了 user_info 接口.";
    }


}
