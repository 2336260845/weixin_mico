package com.dreamer.weixin.async.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.async.EventHandler;
import com.dreamer.weixin.async.EventModel;
import com.dreamer.weixin.async.EventType;
import com.dreamer.weixin.entity.cardSquareBean;
import com.dreamer.weixin.entity.templateBean.TemplateData;
import com.dreamer.weixin.entity.userBean;
import com.dreamer.weixin.service.CardSquareService;
import com.dreamer.weixin.service.UserService;
import com.dreamer.weixin.utils.DateUtil;
import com.dreamer.weixin.utils.JedisAdapter;
import com.dreamer.weixin.utils.MailSender;
import com.dreamer.weixin.utils.WX_TemplateMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class CardHandler implements EventHandler{

    @Autowired
    MailSender mailSender;

    @Autowired
    UserService userService;

    @Autowired
    CardSquareService cardSquareService;

    @Autowired
    WX_TemplateMsgUtil wx_templateMsgUtil;

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 通过finder_card拉取拾取人信息，通过own_card拉取遗失人openid和eamil地址
     * @param model
     */
    @Override
    public void doHandle(EventModel model) {
        //发现用户一卡通，发送邮件通知，实际处理流程
        boolean is_owner = (boolean) model.getExt("is_owner");
        String owner_card = model.getOwnerCard();
        String openid = (String) model.getExt("openid");
        //
        HashMap<String, Object> map = userService.getUserInfoByCode((String) model.getExt("openid"));
        map.put("lost_card",owner_card);
        map.put("find_time", DateUtil.formatDate(new Date()));
        //判断卡来源失主是否信息已存在
        if(is_owner){
            String email = userService.getUserByCard(owner_card).getEmail();
            String to_user_openid = userService.getUserByCard(owner_card).getCode();
            boolean email_flag = mailSender.sendWithHTMLTemplate(email,"喜大奔波，您的一卡通已找到啦！","CardEmailTemplate.ftl",map);
            log.info("发送邮件成功，开始发送根据微信服务号推送消息");
            //整合数据到微信模板
            Map<String,TemplateData> param = new HashMap<>();
            param.put("first",new TemplateData("恭喜一卡通被 " + map.get("finder_name") + " 同学找到啦，请及时联系取回哦","#696969"));
            param.put("lostType",new TemplateData("一卡通","#696969"));
            param.put("lostDescription",new TemplateData(owner_card,"#696969"));
            param.put("phone",new TemplateData((String) map.get("finder_phone"),"#696969"));
            param.put("email",new TemplateData((String) map.get("finder_email"),"#696969"));
            param.put("date",new TemplateData(DateUtil.formatDate(new Date()),"#696969"));
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(param));
            try {
                boolean wechat_flag = wx_templateMsgUtil.sendWeChatMsgToUser(to_user_openid,"","#000000",jsonObject);
                if(!email_flag && !wechat_flag){
                    log.error("微信和邮箱都没推送成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            log.info("开始通过同学进行推送~~~");
            log.info("0-8:" + owner_card.substring(0,8));
            List<userBean> OwnerStudentsCard = userService.getOwnerStudentCard(owner_card.substring(0,8));
            int size = OwnerStudentsCard == null ? 0 : OwnerStudentsCard.size();
            log.info("有" + size + "位同学");
            int flag = size == 0 ? 0 : 1;
            switch (flag){
                case 0:
                    //为0失主和同学都没有注册
                    break;
                case 1:
                    String email = OwnerStudentsCard.get(0).getEmail();
                    mailSender.sendWithHTMLTemplate(email,"喜大奔波，您同学一卡通已找到啦，请及时通知您的同学","StudentCardEmailTemplate.ftl",map);
                    log.info("结束打印第" + flag + "封信");
                    if(flag >= size){
                        break;
                    }
                    flag++;
                case 2:
                    mailSender.sendWithHTMLTemplate(OwnerStudentsCard.get(1).getEmail(),"喜大奔波，您同学一卡通已找到啦，请及时通知您的同学","StudentCardEmailTemplate.ftl",map);
                    log.info("结束打印第" + flag + "封信");
                    if(flag >= size){
                        break;
                    }
                    flag++;
                case 3:
                    mailSender.sendWithHTMLTemplate(OwnerStudentsCard.get(2).getEmail(),"喜大奔波，您同学一卡通已找到啦，请及时通知您的同学","StudentCardEmailTemplate.ftl",map);
                    break;
                default:
                    break;
            }
        }
        //设置捡到一卡通同学半小时内无法投递，以防恶意刷
         jedisAdapter.setEx(openid,openid,600);
//        mailSender.sendWithHTMLTemplate(finder_email,"已经联系遗失人，请注意QQ好友申请或者短信哦","CardEmailTemplate.ftl",map);
        //执行在广场上查找是否有记录,如果没有记录就插入记录,有记录就更新出findcard,findtime
/*        cardSquareBean result = cardSquareService.getCardSquareBeanByCard(owner_card);
        if(result == null){
            cardSquareService.addCardSquareBean(owner_card,userService.getUserByCode(openid).getCard(),find_time,find_time,"0");
        }else{
            cardSquareService.updateCardSquareBean(userService.getUserByCode(openid).getCard(),owner_card,find_time);
        }*/
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.CARD);
    }
}
