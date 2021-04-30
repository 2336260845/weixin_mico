package com.dreamer.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.aop.CodeCheck;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.UserService;

import com.dreamer.weixin.utils.WinxinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 绑定个人信息
 * Created by LiJin on 2019/3/22.
 */
@Slf4j
@Controller
@RequestMapping("/api/v1/wechat/user")
public class AccountBindController {

    @Autowired
    UserService userService;



    Map<String,String> openIdMap = new HashMap<>();

    @RequestMapping(value={"","/index"})
    public String index(){
        return "bindUserInfo";
    }


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap map){
        //执行绑定用户之前是否已经绑定成功
        String code = request.getParameter("code");
        log.info("code为" + code);
        try {
            JSONObject jsonObject = WinxinUtil.getOpenID(code);
            openIdMap.put(code,jsonObject.getString("openid"));
            if(jsonObject.getString("openid") != null || jsonObject.getString("openid") != ""){
                String openid = jsonObject.getString("openid");
                userBean userBean = userService.getUserByCode(openid);
                if(userBean != null){
                    map.put("userBean",userBean);
                    return "bindedUserInfo";
                }else {
                    //如果没绑定成功
                    userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    map.put("bindUser",bindUser);
                    return "index";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@ModelAttribute userBean userBean){
        try{
            String openid = openIdMap.get(userBean.getCode());
            userBean.setCode(openid);
            //查询是否已经被人绑定亲密账号，传入一卡通账号判断
            userBean userBeanFromdb = userService.getUserByCard(userBean.getCard());
            if(userBeanFromdb != null){
                //不等于null，则说明数据库中已经存在了，执行更新操作
                int result = userService.updateUser(userBean);
                if(result >= 0){
                    log.info("绑定用户信息成功");
                    //绑定成功，返回成功界面逻辑
                    return "bindSuccess";
                }
            }
            //不存在该用户信息
            int result = userService.addUser(userBean);
            log.info("result为" + result);
            //如果result不为0，说明已经插入数据成功，则反馈信息给用户，绑定成功
            if(result >= 0){
                log.info("绑定用户信息成功");
                //绑定成功，返回成功界面逻辑
                return "bindSuccess";
            }else {
                //绑定失败，返回失败界面逻辑
            }

        }catch (Exception e){
            log.error("绑定用户信息失败");
            e.printStackTrace();
        }
        return "";
    }
}
