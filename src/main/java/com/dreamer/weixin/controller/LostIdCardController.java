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
import lombok.extern.slf4j.Slf4j;
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

@Controller
@Slf4j
@RequestMapping(value = "/api/v1/wechat/lostidcard")
public class LostIdCardController {

    @Autowired
    UserService userService;

    @Autowired
    IdCardSquareService idCardSquareService;

    @Autowired
    EventProducer eventProducer;

    HashMap<String, String> lostIdCardMap = new HashMap<>();


    @RequestMapping(value = "check", method = RequestMethod.GET)
    public String check(HttpServletRequest request, ModelMap modleMap) {
        //检查拾者是否已经绑定账户信息
        String code = request.getParameter("code");
        try {
            JSONObject jsonObject = com.dreamer.weixin.utils.WinxinUtil.getOpenID(code);
            lostIdCardMap.put(code, jsonObject.getString("openid"));
            // log.info("check ...openid为：" + jsonObject.getString("openid"));
            if (jsonObject.getString("openid") != null || jsonObject.getString("openid") != "") {
                String openid = jsonObject.getString("openid");
                userBean userBean = userService.getUserByCode(openid);
                if (userBean != null) {
                    userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    modleMap.put("bindUser", bindUser);
                    return "findIdCard";
                } else {
                    //如果没绑定成功
                    userBean bindUser = new userBean();
                    bindUser.setCode(code);
                    modleMap.put("bindUser", bindUser);
                    return "index";
                }
            }
        } catch (Exception e) {
            // log.error("检查失败" + e.getMessage());
        }
        return "";
    }

    @RequestMapping(value = "find",method = RequestMethod.POST)
    public String find(@RequestParam("name") String name, @RequestParam("last_four_number") String last_four_number, @RequestParam("code") String code){
        String openid = lostIdCardMap.get(code);
        //last_four_number还没呀
        IdCardBean idCardSquareBen = idCardSquareService.getIdCardSquareBeanByName(name,"1",last_four_number);
        //根据name查询失物广场是否有记录，有记录生成事务，没有就插入信息
        if(idCardSquareBen != null){
            eventProducer.fireEvent(new EventModel(EventType.ID_CARD)
                    .setExt("openid", openid)
                    .setExt("is_openid_lost",true)
                    .setExt("lost_name",name)
                    .setExt("finder_card",idCardSquareBen.getFinder_card()));
            String finder_card = userService.getUserByCode(code).getCard();
            String find_time = DateUtil.formatDate(new Date());
            idCardSquareService.updateLostIdCard(find_time,0);
            return "findSuccess";
        }else{
            String createTime = DateUtil.formatDate(new Date());
            idCardSquareService.insertLostIdCard(name,last_four_number,createTime,1);
            return "";
        }
    }
}
