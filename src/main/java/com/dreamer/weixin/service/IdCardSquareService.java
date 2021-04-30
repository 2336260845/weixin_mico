package com.dreamer.weixin.service;

import com.dreamer.weixin.dao.IdCardSquareDAO;
import com.dreamer.weixin.entity.IdCardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdCardSquareService {

    @Autowired
    IdCardSquareDAO dao;

    //在IdCardSquareBean 更新 finder_card和find_time
    public int updateIdCard( String finder_card,String name ,String find_time,int state){
        return dao.updateIdCard(finder_card,name,find_time,state);
    }

    public IdCardBean getIdCardSquareBeanByName(String name,String state,String last_four_number){
        return dao.getCardSquareBeanByName(name,state,last_four_number);
    }

    //在失物广场插入记录(已处理)
    public int insertIdCard(String name ,String last_four_number,String finder_card , String find_time ,String createTime,int state){
        return  dao.insertIdCard(name ,last_four_number,finder_card , find_time ,createTime,state );
    }

    //在失物广场插入记录(未处理)
    public int addIdCard(String name ,String last_four_number,String finder_card , String createTime,int state){
        return  dao.addIdCard(name ,last_four_number,finder_card , createTime,state);
    }

    //lost
    public int insertLostIdCard(String name ,String last_four_number, String createTime,int state){
        return  dao.insertLostIdCard(name ,last_four_number,createTime,state);
    }

    public int updateLostIdCard(String find_time,int state) {
        return  dao.updateLostIdCard(find_time,state);
    }
}
