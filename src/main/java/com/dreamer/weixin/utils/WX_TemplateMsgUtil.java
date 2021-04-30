package com.dreamer.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.entity.templateBean.TemplateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WX_TemplateMsgUtil {
    private static  final String templatId = "8qebKYyPHYXVvCuKWUOdoQPYvlhqHabaddp_U38E2NE";

    @Autowired
    WinxinUtil winxinUtil;

    /**
     * 封装模板详细信息
     * @return
     */
    public static JSONObject packJsonmsg(Map<String,TemplateData> param){
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,TemplateData> entry : param.entrySet()){
            JSONObject keyJson = new JSONObject();
            TemplateData dta = entry.getValue();
            keyJson.put("value",dta.getValue());
            keyJson.put("color",dta.getColor());
            jsonObject.put(entry.getKey(),keyJson);
        }
        return jsonObject;
    }

    /**
     * 发送模板消息
     */
    public  boolean sendWeChatMsgToUser(String touser,String clickurl,String topcolor,JSONObject data) throws Exception {
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ winxinUtil.getAccessTokenFromRedis();
        JSONObject json = new JSONObject();
        json.put("touser",touser);
        json.put("template_id",templatId);
        json.put("url",clickurl);
        json.put("topcolor",topcolor);
        json.put("data",data);
        try{
            JSONObject result = WinxinUtil.doPostStr(tmpurl,json.toJSONString());
            JSONObject resultJson = new JSONObject(result);
            log.info("发送微信消息返回信息：" + resultJson.get("errcode"));
            String errmsg = (String) resultJson.get("errmsg");
            log.info("发送微信消息返回信息：" + errmsg);
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
