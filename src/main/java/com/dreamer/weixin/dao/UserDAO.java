package com.dreamer.weixin.dao;

import com.dreamer.weixin.entity.userBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, phone, card, email, code, sex";
    String SELECT = " id, " + INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{name},#{phone},#{card},#{email},#{code},#{sex})"})
    int addUser(userBean userBen);

    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where code=#{code}"})
    userBean getUserByCode(String code);

    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where card=#{card}"})
    userBean getUserByCard(String card);

    @Update({"update ",TABLE_NAME," set phone=#{phone},code=#{code},sex=#{sex} where card=#{card}"})
    //int updateUser(@Param("phone") String phone,@Param("code") String code,int sex,String card);
    int updateUser(userBean userBean);

    @Update({"update ",TABLE_NAME," set friend_card=#{card} where code=#{openid}"})
        //int updateUser(@Param("phone") String phone,@Param("code") String code,int sex,String card);
    int updateFriendCardByOpenId(@Param("card") String card,@Param("openid") String openid);

    @Select({"select email from ",TABLE_NAME," where card=#{card}"})
    String getOwnerEmailByCard(String card);

  //  @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where card like #{'card%'}"})
    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where card like CONCAT(#{card},'%')"})
    List<userBean> getOwnerStudentCard(String card);

    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where name=#{name}"})
    List<userBean> getUserByName(String name);


}
