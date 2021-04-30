package com.dreamer.weixin.entity;

public class userBean {

    private int id;
    private String name;
    private String phone;
    private String card;
    private String email;
    private String code;
    private int sex;
    private String friend_card;

    public String getFriend_card() {
        return friend_card;
    }

    public void setFriend_card(String friend_card) {
        this.friend_card = friend_card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
