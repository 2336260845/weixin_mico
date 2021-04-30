package com.dreamer.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.dreamer.weixin.entity.menuentity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/*
 * 微信工具类
 * Created by LiJin on 2019/3/22.
 */
@Slf4j
@Service
public class WinxinUtil {
    public static final String APPID = "wxacd3b7b67763fdf2";
    public static final String APPSECRE = "006952ab1e4f911160b6c567fbfc6156";
    public static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    @Autowired
    JedisAdapter jedisAdapter;

    //get请求
    public static JSONObject doGetStr(String url) throws Exception {

        try {
            java.net.URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b)) != -1){
                sb.append(new String(b,0,len));
            }
            return JSONObject.parseObject(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    //post请求
    public static JSONObject doPostStr(String url,String outStr) throws Exception {

        try{
            java.net.URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            // 要发送数据出去，必须要设置为可发送数据状态
            connection.setDoOutput(true);
            // 获取输出流
            OutputStream os = connection.getOutputStream();
            // 写出数据
            os.write(outStr.getBytes());
            os.close();
            // 获取输入流
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return JSONObject.parseObject(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //获取access_token
    public static AccessToken getAccessToken() throws Exception {
        AccessToken accesstoken = new AccessToken();
        String url = URL.replace("APPID", APPID).replace("APPSECRET", APPSECRE);
        JSONObject jsonObject = doGetStr(url);
        System.out.println(jsonObject);
        if(jsonObject != null) {
            accesstoken.setAccess_token(jsonObject.getString("access_token"));
            accesstoken.setExpires_in(jsonObject.getIntValue("expires_in"));
        }
        return accesstoken;
    }

    public String getAccessTokenFromRedis() throws Exception {
        String weixinAccessToken = jedisAdapter.getFromRedis("weixinAccessToken");
        if(StringUtils.isNoneBlank(weixinAccessToken)){
            return weixinAccessToken;
        }
        String url = URL.replace("APPID", APPID).replace("APPSECRET", APPSECRE);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject.containsKey("access_token")){
            weixinAccessToken = jsonObject.get("access_token").toString();
            log.info("weixinAccessToken" + weixinAccessToken);
            jedisAdapter.setEx("weixinAccessToken",weixinAccessToken,7200);
        }
        return weixinAccessToken;
    }

    //获取openID
    public static JSONObject getOpenID(String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String requestUrl = url.replace("APPID",APPID).replace("SECRET",APPSECRE).replace("CODE",code);
        JSONObject jsonObject = doGetStr(requestUrl);
        return jsonObject;
    }
    //初始化菜单
    @SuppressWarnings("deprecation")
    public static Menu initMenu() {
        Menu menu = new Menu();
        ViewButton button8 = new ViewButton();
        button8.setName("一卡通/学生证");
        button8.setType("view");
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/index&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        //  button8.setUrl("http://www.baidu.com");
        button8.setUrl(url);

        ViewButton button9 = new ViewButton();
        button9.setName("考生证/身份证");
        button9.setType("view");
        button9.setUrl("http://www.baidu.com");

        ViewButton button10 = new ViewButton();
        button10.setName("手机");
        button10.setType("view");
        button10.setUrl("http://www.baidu.com");

        ViewButton button11 = new ViewButton();
        button11.setName("书籍");
        button11.setType("view");
        button11.setUrl("http://www.baidu.com");

        ClickButton button12 = new ClickButton();
        button12.setName("其他");
        button12.setType("click");
        button12.setKey("14");

        Button button3 = new Button();
        button3.setName("我要找");
        button3.setSub_button(new Button[]{button8,button9,button10,button11,button12});



        ViewButton button13 = new ViewButton();
        button13.setName("一卡通/学生证");
        button13.setType("view");
        String find_card_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/findcard/check&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button13.setUrl(find_card_url);

        ClickButton button14 = new ClickButton();
        button14.setName("考试证/身份证");
        button14.setType("click");
        button14.setKey("13");

        ViewButton button15 = new ViewButton();
        button15.setName("手机");
        button15.setType("view");
        String find_phone_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/findPhone/index&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button15.setUrl(find_phone_url);

        ViewButton button16 = new ViewButton();
        button16.setName("书籍");
        button16.setType("view");
        String find_book_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/findBook/index&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button16.setUrl(find_book_url);

        ViewButton button17 = new ViewButton();
        button17.setName("其他");
        button17.setType("view");
        String find_other_things_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/findOtherThings/index&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button17.setUrl(find_other_things_url);

        Button button2 = new Button();
        button2.setName("我拾到");
        button2.setSub_button(new Button[]{button13,button14,button15,button16,button17});

        ViewButton button31 = new ViewButton();
        button31.setName("个人账号");
        button31.setType("view");
        String account_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/user/check&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button31.setUrl(account_url);

        ViewButton button32 = new ViewButton();
        button32.setName("亲密账号");
        button32.setType("view");
        String friend_account_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb127bd05033d3e10&redirect_uri=http://g4bvda.natappfree.cc/api/v1/wechat/friend/index&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        button32.setUrl(friend_account_url);

        Button button = new Button();
        button.setName("我要绑定");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button3,button2,button});
        return menu;
    }

    public static int createMenu(String menu,String accessToken) throws Exception {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
        System.out.println(url);
        JSONObject jsonObject = doPostStr(url, menu);
        if(jsonObject!=null) {
            result = jsonObject.getIntValue("errcode");
        }
        return result;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
