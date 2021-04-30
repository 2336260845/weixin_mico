package com.dreamer.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeChatMenuUtil implements InitializingBean {

    @Autowired
    WinxinUtil winxinUtil;

    @Override
    public void afterPropertiesSet() throws Exception {
        String accesstoken = "";
        try {
            accesstoken = winxinUtil.getAccessTokenFromRedis();
            System.out.println("accesstoken:"+accesstoken);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String menu = JSONObject.toJSONString(WinxinUtil.initMenu()).toString();
        System.out.println(menu);
        try {
            int result = WinxinUtil.createMenu(menu, accesstoken);
            if(result==0) {
                System.out.println("菜单创建成功");
            }else{
                System.out.println(result);
                System.out.println("菜单创建失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
