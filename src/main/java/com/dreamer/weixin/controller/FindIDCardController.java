package com.dreamer.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.aop.CodeCheck;
import com.dreamer.weixin.async.EventModel;
import com.dreamer.weixin.async.EventProducer;
import com.dreamer.weixin.async.EventType;
import com.dreamer.weixin.entity.IdCardBean;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.IdCardSquareService;
import com.dreamer.weixin.service.UserService;
import com.dreamer.weixin.utils.DateUtil;
import com.dreamer.weixin.utils.WinxinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class FindIDCardController {

    @Autowired
    IdCardSquareService idCardSquareService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    UserService userService;

    HashMap<String,String> findIdCardMap = new HashMap<>();

    @RequestMapping(value={"","/index"})
    public String index(){
        return "findIdCard";
    }


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap modelMap){
        //检查拾者是否已经绑定账户信息
        String code = request.getParameter("code");
        try{
            JSONObject jsonObject = WinxinUtil.getOpenID(code);
            findIdCardMap.put(code,jsonObject.getString("openid"));
            //log.info("check ...openid为：" + jsonObject.getString("openid"));
            if (jsonObject.getString("openid") != null || jsonObject.getString("openid") != ""){
                String openid = jsonObject.getString("openid");
                userBean userBean = userService.getUserByCode(openid);
                if(userBean != null){
                    com.dreamer.weixin.entity.userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    modelMap.put("bindUser",bindUser);
                    return "findIdCard";
                }else {
                    //如果没绑定成功
                    com.dreamer.weixin.entity.userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    modelMap.put("bindUser",bindUser);
                    return "index";
                }
            }
        }catch (Exception e){
            //log.error("检查失败" + e.getMessage());
        }
        return "";
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    public String find(@RequestParam("name") String name, @RequestParam("last_four_number") String last_four_number, @RequestParam("code") String code) {
        String openid = findIdCardMap.get(code);
        //查询失主的名字,如果可以找到就生成事务，沒有找到就插入信息
        List<userBean> owener_name = userService.getUserByName(name);
        if(owener_name != null && owener_name.size() != 0){
            eventProducer.fireEvent(new EventModel(EventType.ID_CARD)
                    .setExt("openid",openid)
                    .setExt("is_openid_lost",false)
                    .setExt("last_four_number",last_four_number)
                    .setExt("name",owener_name));
            IdCardBean idCardBean = idCardSquareService.getIdCardSquareBeanByName(name,"1",last_four_number);
            if(idCardBean == null){
                String time = DateUtil.formatDate(new Date());
                idCardSquareService.insertIdCard(name,last_four_number,userService.getUserByCode(code).getCard(), time,time,0);
            }else {
                idCardSquareService.updateIdCard(userService.getUserByCode(code).getCard(),name,DateUtil.formatDate(new Date()),0);
            }
            return "findSuccess";
        }else {
            idCardSquareService.addIdCard(name,last_four_number,userService.getUserByCode(code).getCard(),DateUtil.formatDate(new Date()),1);
        }
        return "";
    }
}
