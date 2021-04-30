package com.dreamer.weixin.service;

import com.dreamer.weixin.dao.UserDAO;
import com.dreamer.weixin.entity.userBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public int addUser(userBean userBean){
        return userDAO.addUser(userBean);  //>= 0 ? userBean.getId() : 0
    }

    public userBean getUserByCode(String code){
        return userDAO.getUserByCode(code);
    }

    public userBean getUserByCard(String card){
        return userDAO.getUserByCard(card);
    }

    public HashMap<String,Object> getUserInfoByCode(String openid){
        //数据异常检测
        HashMap<String,Object> userMap = new HashMap<>();
        log.info("openid为：" + openid);
        userBean userBean = userDAO.getUserByCode(openid);
        userMap.put("finder_name",userBean.getName());
        userMap.put("finder_phone",userBean.getPhone());
        userMap.put("finder_email",userBean.getEmail());
        return userMap;
    }

    public int updateUser(userBean userBean){
        return userDAO.updateUser(userBean);
    }

    public int updateFriendCardByOpenId(String card,String openid){
        return userDAO.updateFriendCardByOpenId(card,openid);
    }
    //根据学号查询email
    public String getOwnEmailByCard(String card){
        return userDAO.getOwnerEmailByCard(card);
    }

    //根据失主学号前8位，查找失主的同班同学的学号
    public List<userBean> getOwnerStudentCard(String partcard){
        return userDAO.getOwnerStudentCard(partcard);
    }

    public List<userBean> getUserByName(String name){
        return userDAO.getUserByName(name);
    }

}
