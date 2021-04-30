package com.dreamer.weixin.dao;

import com.dreamer.weixin.entity.cardSquareBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CardSquareDAO {
    String TABLE_NAME = " cardSquare ";
    String INSERT_FIELDS = " card, finder_card, find_time, create_time, state";

    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where code=#{code}"})
    cardSquareBean getCardSquareBeanByCard(String card);

    @Update({"update ",TABLE_NAME," set finder_card=#{finder_card}, find_time = #{find_time} where card=#{card}"})
    int updateCardSquareBean( String finder_card,String card ,String find_time);

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{card},#{finder_card},#{find_time},#{create_time},#{state})"})
    int addCardSquareBean(String card ,String finder_card , String find_time ,String create_time,String state );

    @Update({"update ",TABLE_NAME," set  find_time = #{find_time} where card=#{card}"})
    int updateCardSquareBeanFind_time( String card ,String find_time);
}
