package com.dreamer.weixin.dao;

import com.dreamer.weixin.entity.IdCardBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IdCardSquareDAO {

    String TABLE_NAME = " idCardSquare ";
    String INSERT_FIELDS = " name, last_four_number, finder_card, find_time, createTime, state";

    @Select({"select * from ",TABLE_NAME," where name=#{name} and state = #{state} and last_four_number = #{last_four_number}"})
    IdCardBean getCardSquareBeanByName(String name,String state,String last_four_number);

    //已挂失
    @Update({"update ",TABLE_NAME," set finder_card=#{finder_card}, find_time = #{find_time},state = #{state} where name=#{name}"})
    int updateIdCard( String finder_card,String name ,String find_time,int state);

    //没有挂失记录
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS, ") values (#{name},#{last_four_number},#{finder_card},#{find_time},#{createTime},#{state})"})
    int insertIdCard(String name ,String last_four_number,String finder_card ,String find_time,String createTime,int state );

    //没有找到owner
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{last_four_number},#{finder_card},#{createTime},#{state})"})
    int addIdCard(String name,String last_four_number,String finder_card, String createTime,int state);

    //lost
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{last_four_number},#{finder_card},#{createTime},#{state})"})
    int insertLostIdCard(String name ,String last_four_number, String createTime,int state );

    @Update({"update ",TABLE_NAME," set find_time = #{find_time},state = #{state} where name=#{name}"})
    int updateLostIdCard( String find_time,int state);
}
