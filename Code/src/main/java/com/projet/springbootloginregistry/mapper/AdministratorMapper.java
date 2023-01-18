package com.projet.springbootloginregistry.mapper;

import com.projet.springbootloginregistry.pojo.Administrator;
import com.projet.springbootloginregistry.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdministratorMapper {
    @Delete("DELETE FROM user WHERE email =#{email}")
    int deleteByEmail(@Param("email") String email);

    @Select("SELECT email, password FROM administrator WHERE email =#{email}")
    List<Administrator > selectAdministratorByEmail(@Param("email") String email);

}
