package com.projet.springbootloginregistry.mapper;

import com.projet.springbootloginregistry.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /**
     *
     * @param user
     * @return
     */
    @Insert("INSERT INTO user ( email, password, salt, confirm_code, activation_time,is_valid,first_name,last_name,id_equipment,Borrow)" +
            "VALUES(#{email},#{password},#{salt},#{confirmCode},#{activationTime},#{isValid},#{firstName},#{lastName},#{idEquipment},#{borrow})")
    int insertUser(User user);

    /**
     * Interroger l'utilisateur en fonction du code de confirmation
     * @param confirmCode
     * @return
     */
    @Select("SELECT email ,activation_time FROM user WHERE confirm_code = #{confirmCode} AND is_valid = 0 ")
    User selectUserByConfirmCode(@Param("confirmCode")String confirmCode);

    /**
     * Interroger l'utilisateur en fonction du code de confirmation et modifier  l'etat de l'utilisateur  a 1
     * @param confirmCode
     * @return
     */
    @Update("UPDATE user SET is_valid = 1 WHERE confirm_code = #{confirmCode}")
    int updateUserByConfirmCode(@Param("confirmCode")String confirmCode);

    /**
     * Interroger l es utilisteur en fonction des emails
     * @param email
     * @return
     */
    @Select("SELECT email, password ,salt,first_name, Borrow, id_equipment FROM user WHERE email =#{email} AND is_valid =1")
    List<User> selectUserByEmail(@Param("email") String email);

    @Select("SELECT email, password ,salt FROM user WHERE email =#{email}")
    List<User> selectUserByEmailNonValid(@Param("email") String email);



    @Update("UPDATE user SET password = #{password}, salt = #{salt} WHERE email =#{email}")
    int updateUserByEmail(User user);

    @Update("UPDATE user SET borrow =1, id_equipment = #{idEquipment} WHERE email = #{email}" )
    int updateUserBorrowByEmail(User user);

    @Update("UPDATE user SET borrow =0,  id_equipment = 0 WHERE email = #{email}" )
    int updateUserReturnByEmail(User user);

}
