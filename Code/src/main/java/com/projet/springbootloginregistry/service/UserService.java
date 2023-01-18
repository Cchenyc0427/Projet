package com.projet.springbootloginregistry.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.projet.springbootloginregistry.mapper.EquipmentMapper;
import com.projet.springbootloginregistry.mapper.UserMapper;
import com.projet.springbootloginregistry.pojo.Equipment;
import com.projet.springbootloginregistry.pojo.User;
import jakarta.annotation.Resource;
import jdk.jfr.TransitionTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailService mailService;
    @Resource
    private EquipmentMapper equipmentMapper;

    private User userTemporary;

    private Equipment equipmentTemporary;
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createAccount(User user){
        //Generer le code de confirmation par l'algorithme snowflake
        String confirmCode = IdUtil.getSnowflake(1,1).nextIdStr();

        //Crypter par sel
        String salt = RandomUtil.randomString(6);

        String md5Pwd = SecureUtil.md5(user.getPassword()+salt);
        LocalDateTime ldt= LocalDateTime.now().plusDays(1);

        //Initialiser
        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setSalt(salt);
        user.setPassword(md5Pwd);
        user.setConfirmCode(confirmCode);
        user.setActivationTime(ldt);
        user.setIsValid((byte) 0);
        user.setBorrow((byte)0);
        user.setIdEquipment(0);

        Map<String, Object> resultMap= new HashMap<>();

        int result=0;
        List<User> userList =userMapper.selectUserByEmailNonValid(user.getEmail());
        if(userList == null || userList.isEmpty()){
             result= userMapper.insertUser(user);
        }
        // Ajouter compte
        if(result >0){
            //TODO
            String activationUrl = "http://localhost:8080/user/activation?confirmCode="+confirmCode;
            mailService.sendMailActivationAccount(activationUrl,user.getEmail());

            resultMap.put("code",200);
            resultMap.put("message","Reussit");

        }else{
            resultMap.put("code",400);
            resultMap.put("message","account exist");
        }
        return resultMap;
    }

    /**
     *
     * @param user
     * @return
     */
    public Map<String,Object> loginAccount(User user){
        Map<String, Object> resultMap =new HashMap<>();
        //Interroger l es utilisteur en fonction des emails
        List<User> userList =userMapper.selectUserByEmail(user.getEmail());
        // S'il n'y a pas des resultat, return : account not exist or no activation
        if(userList == null || userList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","account not exist or no activation");
            return resultMap;
        }
        //s'il y a beaucoup des comptes, return: account abnormal
        if(userList.size()>1){
            resultMap.put("code",400);
            resultMap.put("message","account abnormal");
            return resultMap;
        }
        //Chercher un compte, et comparer des mots de passe
        User u=userList.get(0);
        String md5Pwd =SecureUtil.md5(user.getPassword()+u.getSalt());

        //mots de passe n'est pas meme
        if(!u.getPassword().equals(md5Pwd)){
            resultMap.put("code",400);
            resultMap.put("message","The account or password is incorrect");
            return resultMap;
        }
       userTemporary = userList.get(0);
        resultMap.put("code",200);
        resultMap.put("message ","Log in successfully");
        return resultMap;
    }


    public Map<String, Object> updatePassword(User user){
        Map<String,Object> resultMap =new HashMap<>();
        List<User> userList =userMapper.selectUserByEmail(user.getEmail());
        if(userList == null || userList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","account not exist or no activation");
            return resultMap;
        }

        User u=userList.get(0);
        String salt = RandomUtil.randomString(6);
        String md5Pwd = SecureUtil.md5(user.getPassword()+salt);
        u.setPassword(md5Pwd);
        u.setSalt(salt);

        int result= userMapper.updateUserByEmail(u);
        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","Reussit");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","update failed");
        }
        return resultMap;
    }

    public Map<String, Object> activationAccount(String confirmCode) {
        Map<String, Object> resultMap= new HashMap<>();
        User user =userMapper.selectUserByConfirmCode(confirmCode);

        boolean after = LocalDateTime.now().isAfter(user.getActivationTime());
        if(after){
            resultMap.put("code",400);
            resultMap.put("message","The link is no longer available, please re-register");

        }
        int result  =userMapper.updateUserByConfirmCode(confirmCode);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","activation successed");
        }else{
            resultMap.put("code",200);
            resultMap.put("message","activation failed");
        }


        return resultMap;
    }


    public Map<String, Object> borrowEquipment(Equipment equipment){
        Map<String,Object> resultMap =new HashMap<>();
        List<Equipment> equipmentList = equipmentMapper.selectEquipmentById(equipment.getId());
        List<User> userList = userMapper.selectUserByEmail(userTemporary.getEmail());
        userTemporary = userList.get(0);

        if(equipmentList == null || equipmentList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","this item not exist");
            return resultMap;
        }else {
            int resultEquipment = 0;
            int resultUser = 0;
            Equipment e = equipmentList.get(0);
            if (e.getIsBorrow() == 0) {
                if (userTemporary.getBorrow() == 0) {
                    e.setUserName(userTemporary.getFirstName());
                    userTemporary.setIdEquipment(equipment.getId());
                    resultEquipment = equipmentMapper.borrowEquipment(e);
                    resultUser = userMapper.updateUserBorrowByEmail(userTemporary);
                    resultMap.put("code", 200);
                    resultMap.put("message", "borrow!");
                } else {
                    resultMap.put("code", 400);
                    resultMap.put("message", "you have borrowed one item, please return it and then borrow another");
                }
            } else {
                resultMap.put("code", 400);
                resultMap.put("message", "this item has been borrowed");
            }
        }


//        if(resultEquipment>0){
//            if(resultUser>0){
//                resultMap.put("code",200);
//                resultMap.put("message","Borrow!");
//            }else{
//                resultMap.put("code",400);
//                resultMap.put("message","update equipment failed");
//            }
//        }else{
//            resultMap.put("code",400);
//            resultMap.put("message","update user information failed");
//        }


        return resultMap;
    }

    public Map<String, Object> returnEquipment(Equipment equipment){
        Map<String,Object> resultMap =new HashMap<>();
        List<Equipment> equipmentList = equipmentMapper.selectEquipmentById(equipment.getId());
        List<User> userList = userMapper.selectUserByEmail(userTemporary.getEmail());
        userTemporary = userList.get(0);
        int resultEquipment = 0 ;
        int resultUser = 0 ;
        if(equipmentList == null || equipmentList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","this item not exist");
            return resultMap;
        }else{
            Equipment e = equipmentList.get(0);
            if(userTemporary.getIdEquipment()==e.getId()){
                resultEquipment=equipmentMapper.returnEquipment(e);
                resultUser =userMapper.updateUserReturnByEmail(userTemporary);
                resultMap.put("code",200);
                resultMap.put("message","return!");
            }else{
                resultMap.put("code",400);
                resultMap.put("message","you dont borrow this item");
            }

        }

        return resultMap;
    }


}
