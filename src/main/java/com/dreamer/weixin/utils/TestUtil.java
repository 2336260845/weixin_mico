package com.dreamer.weixin.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {
    public static void main(String[] agrs){
   //     int size = OwnerStudentsCard == null ? 0 : OwnerStudentsCard.size();
        int size = 1;
        int flag = 1;
        switch (flag){
            case 0:
                //为0失主和同学都没有注册
                break;
            case 1:
                log.info("开始打印:" + flag);
                if(flag >= size){
                    break;
                }
                flag++;
            case 2:
                log.info("开始打印:" + flag);
                if(flag >= size){
                    break;
                }else {
                    flag++;
                }
            case 3:
                log.info("开始打印:" + flag);
                break;
            default:
                break;
        }
    }
}
