package com.dreamer.weixin.controller;

import com.dreamer.weixin.service.WeixinService;
import com.dreamer.weixin.utils.WinxinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WeChatSignController {
    private static final String TOKEN = "yeying";
    /**
     * 微信Token验证
     * @param signature	微信加密签名
     * @param timestamp	时间戳
     * @param nonce		随机数
     * @param echostr	随机字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @RequestMapping("get")
    public void getToken(String signature,String timestamp,String nonce,String echostr,HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        if(WeixinService.check(TOKEN,timestamp,nonce,signature)){
            log.info("微信校验接入成功");
            PrintWriter out = response.getWriter();
            //原样返回echostr参数
            out.print(echostr);
            out.flush();
            out.close();
        }else {
            log.error("微信校验接入失败");
        }
    }
}
