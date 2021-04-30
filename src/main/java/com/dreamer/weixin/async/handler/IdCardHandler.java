package com.dreamer.weixin.async.handler;

import com.dreamer.weixin.async.EventHandler;
import com.dreamer.weixin.async.EventModel;
import com.dreamer.weixin.async.EventType;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.UserService;
import com.dreamer.weixin.utils.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class IdCardHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        //发现用户身份证考试证，发送邮件通知，实际处理流程
        boolean is_openid_lost = (boolean) model.getExt("is_openid_lost");
        String openid = String.valueOf(model.getExt("openid"));
        if(is_openid_lost){
            //发起事件的是本人，即为挂失,openid即为遗失人的openid
            String finder_card = String.valueOf(model.getExt("finder_card"));
            userBean finder_userBean = userService.getUserByCard(finder_card);
            userBean lost_userBean = userService.getUserByCode(openid);
            HashMap<String,Object> map = userService.getUserInfoByCode(finder_userBean.getCode());

            mailSender.sendWithHTMLTemplate(lost_userBean.getEmail(),"喜大奔波，您的考试证/身份证已找到啦！","CardEmailTemplate",map);
            mailSender.sendWithHTMLTemplate(finder_userBean.getEmail(),"已经联系失主，请保持联系","CardEmailTemplate",map);
        }else {
            //发起事件的是拾取人，即为拾取,发起事件的是拾取人，且数据库中存在此人，直接推给可能存在的人,可能有几个人
            String last_four_number = String.valueOf(model.getExt("last_four_number"));
            HashMap<String,Object> map = userService.getUserInfoByCode(openid);   //map存的是拾取人的个人信息
            map.put("the_last_number",last_four_number);
            List<userBean> userBeanList = (List<userBean>) model.getExt("name");   //获取可能存在的多个联系人
            log.info("开始发送邮件给潜在的失主");
            for(int i = 1;i <= userBeanList.size();i++){
                mailSender.sendWithHTMLTemplate(userBeanList.get(i).getEmail(),"喜大奔波，您的考试证/身份证已找到啦！","CardEmailTemplate",map);
            }
            mailSender.sendWithHTMLTemplate(String.valueOf(map.get("finder_email")),"已经联系失主，请保持联系","CardEmailTemplate",map);
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ID_CARD);
    }
}
