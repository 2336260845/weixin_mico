package com.dreamer.weixin.service;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/*
 * 微信接口调用检测类
 * Created by LiJin on 2019/3/22.
 */
@Slf4j
public class WeixinService {
    /**
     * 验证签名
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     * by 罗召勇 Q群193557337
     */
    public static boolean check(String TOKEN, String timestamp, String nonce, String signature) {
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[] {TOKEN,timestamp,nonce};
        log.info("叶莹测试：TOKEN=" +TOKEN);
        log.info("叶莹测试：timestamp=" +timestamp);
        log.info("叶莹测试：nonce=" +nonce);
        log.info("叶莹测试：signature=" +signature);
        if (TOKEN == null || timestamp == null || nonce == null) {
            log.info("参数存在为空,无法校验");
            return false;
        }
        Arrays.sort(strs);
        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0]+strs[1]+strs[2];
        log.info("叶莹测试：str=" + str);
        String mysig = sha1(str);
        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        log.info("叶莹测试：musig=" + mysig);
        return mysig.equalsIgnoreCase(signature);
    }

    /**
     * 进行sha1加密
     * @param src
     * @return
     */
    private static String sha1(String src) {
        try {
            //获取一个加密对象
            MessageDigest md = MessageDigest.getInstance("sha1");
            //加密
            byte[] digest = md.digest(src.getBytes());
            char[] chars= {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
            StringBuilder sb = new StringBuilder();
            //处理加密结果
            for(byte b:digest) {
                sb.append(chars[(b>>4)&15]);
                sb.append(chars[b&15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
