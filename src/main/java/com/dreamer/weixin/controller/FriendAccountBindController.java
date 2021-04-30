package com.dreamer.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.aop.CodeCheck;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.UserService;
import com.dreamer.weixin.utils.WinxinUtil;
import freemarker.ext.jsp.FreemarkerTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 绑定亲密朋友信息
 *  Created by LiJin on 2019/3/22.
 */
@Slf4j
@Controller
@RequestMapping("/api/v1/wechat/friend")
public class FriendAccountBindController {

    @Autowired
    UserService userService;

    Map<String,String> friendMap = new HashMap<>();

    @RequestMapping(value={"","/index"})
    public String index(){
        return "bindFriendInfo";
    }


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap modelMap){
        String code = request.getParameter("code");
        //判断是否已经绑定好友,查询friend_card是否为null
        try{
            if(code != null) {
                JSONObject jsonObject = WinxinUtil.getOpenID(code);
                String openid = jsonObject.getString("openid");
                friendMap.put(code,openid);
                userBean userBean = userService.getUserByCode(openid);
                if(userBean.getFriend_card() != null){
                    //绑定的friend_card不为null，则说明已经绑定了
                    modelMap.put("userBean",userBean);
                    return "bindedUserInfo";
                }else {
                    userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    modelMap.put("bindUser",bindUser);
                    return "bindFriendInfo";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "bind",method = RequestMethod.POST)
    public String bind(@ModelAttribute userBean userBean){
        //开始绑定朋友信息
        log.info("开始绑定朋友信息");
        //根据card是否已经存在信息
        String card = userBean.getCard();
        String openid = friendMap.get(userBean.getCode());
        int result = userService.updateFriendCardByOpenId(userBean.getCard(),openid);
        if(result >= 0){
            log.info("绑定朋友信息成功");
            //执行用户亲密账号是否已经自行绑定系统，如果没有，则系统自动添加
            userBean userBeanFromdb = userService.getUserByCard(card);
            if(userBeanFromdb == null) {
                //朋友没有绑定账号,直接进行插入操作
                userService.addUser(userBean);
            }
            return "bindSuccess";
        }

        return "";

    }
}
