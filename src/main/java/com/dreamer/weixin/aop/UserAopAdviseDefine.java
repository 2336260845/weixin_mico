package com.dreamer.weixin.aop;


import com.dreamer.weixin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class UserAopAdviseDefine {

    @Autowired
    UserService userService;

    @Pointcut("@annotation(com.dreamer.weixin.aop.CodeCheck)")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object checkCode(ProceedingJoinPoint joinPoint) throws Throwable {
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
               .getRequest();
       log.info("进入aop");
       String code = request.getParameter("code") == null ? "" : request.getParameter("code");
       if (code.equals("")){
               //如果检查到code为空，说明不是来自于合法微信端，则拒绝操作
               log.info("获取到");
               return "bindUserInfo";
       }else {
           return "bindUserInfo";
       }
    }

}
