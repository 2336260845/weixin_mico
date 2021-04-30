package com.dreamer.weixin.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class IdCardBean {
    private int id;
    private String name;
    private String finder_card;
    private String last_four_number;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinder_card() {
        return finder_card;
    }

    public void setFinder_card(String finder_card) {
        this.finder_card = finder_card;
    }

    public String getLast_four_number() {
        return last_four_number;
    }

    public void setLast_four_number(String last_four_number) {
        this.last_four_number = last_four_number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
