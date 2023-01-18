package com.projet.springbootloginregistry.mapper;

import com.projet.springbootloginregistry.pojo.Equipment;
import com.projet.springbootloginregistry.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EquipmentMapper {

    @Select("SELECT * FROM equipment WHERE id =#{id} ")
    List<Equipment> selectEquipmentById(@Param("id") int id);

    @Select("SELECT * FROM equipment WHERE name = #{name}")
    List<Equipment> selectEquipmentByName(@Param("name") String name);

    @Select("SELECT * FROM equipment ")
    List<Equipment> selectAllEquipment();

    @Insert("INSERT INTO equipment (name) VALUES(#{name})")
    int insertEquipment(Equipment equipment);

    @Delete("DELETE FROM equipment WHERE id =#{id}")
    int deleteById(@Param("id") int id);

    @Update("UPDATE equipment SET is_Borrow = 1, userName = #{userName} WHERE id = #{id} ")
    int borrowEquipment(Equipment equipment);

    @Update("UPDATE equipment SET is_Borrow = 0, userName = 0 WHERE id = #{id}")
    int returnEquipment(Equipment equipment);



}
