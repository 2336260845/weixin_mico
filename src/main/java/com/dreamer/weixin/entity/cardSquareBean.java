package com.dreamer.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class cardSquareBean {

    private int id;
    private String card;
    private String finder_card;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date find_time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getFinder_card() {
        return finder_card;
    }

    public void setFinder_card(String finder_card) {
        this.finder_card = finder_card;
    }

    public Date getFind_time() {
        return find_time;
    }

    public void setFind_time(Date find_time) {
        this.find_time = find_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
