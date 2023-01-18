package com.projet.springbootloginregistry.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.projet.springbootloginregistry.mapper.AdministratorMapper;
import com.projet.springbootloginregistry.mapper.UserMapper;
import com.projet.springbootloginregistry.pojo.Administrator;
import com.projet.springbootloginregistry.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class AdministratorService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdministratorMapper administratorMapper;


    public Map<String,Object> deleteAccount(User user){
        Map<String,Object> resultMap =new HashMap<>();
        List<User> userList = userMapper.selectUserByEmail(user.getEmail());
        if(userList==null || userList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","account not exist or no activation");
            return resultMap;
        }
        int result =0;
        User u=userList.get(0);
        if(u.getBorrow()==1){
            resultMap.put("code",400);
            resultMap.put("message","this user has borrowed one item, you can't delete him now");
        }else{
            result =administratorMapper.deleteByEmail(u.getEmail());
            if(result>0){
                resultMap.put("code",200);
                resultMap.put("message","delete successed");
            }else{
                resultMap.put("code",400);
                resultMap.put("message","delete failed");
            }
        }

        return resultMap;
    }

    public Map<String,Object> loginAdministrator(Administrator administrator){
        Map<String,Object> resultMap = new HashMap<>();
        List<Administrator> administratorList = administratorMapper.selectAdministratorByEmail(administrator.getEmail());
        if(administratorList == null || administratorList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","account not exist or no activation");
            return resultMap;
        }
        if(administratorList.size()>1){
            resultMap.put("code",400);
            resultMap.put("message","account abnormal");
            return resultMap;
        }
        Administrator a=administratorList.get(0);


        if(!a.getPassword().equals(administrator.getPassword())){
            resultMap.put("code",400);
            resultMap.put("message","The account or password is incorrect");
            return resultMap;
        }
        resultMap.put("code",200);
        resultMap.put("message ","Log in successfully");
        return resultMap;
    }

}
