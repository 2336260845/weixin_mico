package com.dreamer.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 拾到手机
 * Created by LiJin on 2019/3/22.
 */
@Controller
@RequestMapping("/api/v1/wechat/findPhone")
public class FindPhoneController {

    @RequestMapping(value={"","/index"})
    public String index(){
        return "findPhone";
    }
}
