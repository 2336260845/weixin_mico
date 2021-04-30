package com.dreamer.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.aop.CodeCheck;
import com.dreamer.weixin.async.EventModel;
import com.dreamer.weixin.async.EventProducer;
import com.dreamer.weixin.async.EventType;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.CardSquareService;
import com.dreamer.weixin.service.UserService;
import com.dreamer.weixin.utils.JedisAdapter;
import com.dreamer.weixin.utils.WinxinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 找到一卡通
 */
@Controller
@Slf4j
@RequestMapping(value = "/api/v1/wechat/findcard")
public class FindCardController {

    @Autowired
    CardSquareService cardSquareService;

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    JedisAdapter jedisAdapter;

    HashMap<String,String> findCardMap = new HashMap<>();

    @RequestMapping(value={"","/index"})
    public String index(){
        return "findCard";
    }


    @RequestMapping(value = "check",method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap modelMap){
        //检查拾者是否已经绑定账户信息
        String code = request.getParameter("code");
        try{
            JSONObject jsonObject = WinxinUtil.getOpenID(code);
            findCardMap.put(code,jsonObject.getString("openid"));
            log.info("check ...openid为：" + jsonObject.getString("openid"));
            if (jsonObject.getString("openid") != null || jsonObject.getString("openid") != ""){
                String openid = jsonObject.getString("openid");
                boolean flag = jedisAdapter.getFromRedis(openid) == null ? true : false;
                if(flag){
                    //没有重复提交
                    userBean userBean = userService.getUserByCode(openid);
                    if(userBean != null){
                        userBean bindUser = new userBean();
                        bindUser.setCode(code);
                        modelMap.put("bindUser",bindUser);
                        return "findCard";
                    }else {
                        //如果没绑定成功
                        userBean bindUser = new userBean();
                        bindUser.setCode(code);
                        modelMap.put("bindUser",bindUser);
                        return "index";
                    }
                }else {
                    //有重复提交
                    return "RepeatFind";
                }
            }
        }catch (Exception e){
            log.error("检查失败" + e.getMessage());
        }
        return "404";
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    public String find(@RequestParam("card") String card,@RequestParam("code") String code){
        //通过code从findCardMap获取到openid，再通过其查询拾者的个人信息，查询一卡通挂失广场是否已经存在此卡,,, 拒绝找到自己的卡
        String openid = findCardMap.get(code);
        //查询失主的联系方式，如果可以找到就生成事务，否则查询其同学的账号用其同学的学号生成事务
        String OwnerEmail = userService.getOwnEmailByCard(card);
        if (OwnerEmail != null && OwnerEmail != "") {
                eventProducer.fireEvent(new EventModel(EventType.CARD)
                        .setExt("openid", openid)
                        .setExt("is_owner", true)
                        .setOwnerCard(card));
                return "findSuccess";
        } else {
                log.info("通过同学推送");
                eventProducer.fireEvent(new EventModel(EventType.CARD)
                        .setExt("openid", openid)
                        .setExt("is_owner", false)
                        .setOwnerCard(card));
                return "findSuccess";
        }
    }
}
