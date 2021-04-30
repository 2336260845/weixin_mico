package com.dreamer.weixin.controller;

import com.dreamer.weixin.service.CardSquareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 遗失一卡通入口
 *
 */
@Controller
@Slf4j
@RequestMapping(value = "/api/v1/wechat/lostcard")
public class LostCardController {


    @Autowired
    CardSquareService cardSquareService;

    Map<String,String> lostCodeMap = new HashMap<>();


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap modelMap){
        //check是否已经绑定个人账号，如果已经绑定则返回到找一卡通界面，反则跳转到绑定一卡通界面
        return "";
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    public String find(@RequestParam("card") String username,@RequestParam("code") String code){
        //通过card查询数据库中遗失人的信息，并查询一卡通遗失广场是否已经存在此张卡的记录
        return "";
    }

}
