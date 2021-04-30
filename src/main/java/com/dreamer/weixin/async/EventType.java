package com.dreamer.weixin.async;

public enum  EventType {
    CARD(0),              // 一卡通标识
    STUDENT_CARD(1),      //  学生证
    ID_CARD(2),           // 考试证/身份证标识
    PHONE(3),             // 手机标识
    BOOK(4),              // 书籍标识
    OTHER(5);              // 其他标识

    private int value;
    EventType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
