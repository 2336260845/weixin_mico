package com.dreamer.weixin.service;

import com.dreamer.weixin.dao.CardSquareDAO;
import com.dreamer.weixin.entity.cardSquareBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardSquareService {
    @Autowired
    CardSquareDAO cardSquareDAO;


    //查询CardSquareBean by card
    public cardSquareBean getCardSquareBeanByCard(String card){
        return cardSquareDAO.getCardSquareBeanByCard(card);
    }

    //在CardSquareBean 更新 finded-card
    public int updateCardSquareBean( String finder_card,String card ,String find_time){
        return cardSquareDAO.updateCardSquareBean(finder_card,card,find_time);
    }

    //在失物广场添加一条记录
    public int addCardSquareBean(String card ,String finder_card , String find_time ,String create_time,String state){
        return  cardSquareDAO.addCardSquareBean(card,finder_card,find_time,create_time,state);
    }

    //更新找到的时间
    public int updateCardSquareBeanFind_time(String find_time ,String card){
        return cardSquareDAO.updateCardSquareBeanFind_time(card , find_time);
    }
}
