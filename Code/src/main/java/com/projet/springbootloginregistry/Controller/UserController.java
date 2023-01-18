package com.projet.springbootloginregistry.Controller;


import com.projet.springbootloginregistry.pojo.Equipment;
import com.projet.springbootloginregistry.pojo.User;
import com.projet.springbootloginregistry.service.UserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     *
     */
    @Resource
    private UserService userService;
    /**
     *
     * @param user
     * @return
     */
    @PostMapping("create")
    public Map<String,Object> createAccount(User user){
        return userService.createAccount(user);
    }

    @PostMapping("login")
    public Map<String, Object> loginAccount(User user){
        return userService.loginAccount(user);
    }

    @PostMapping("update")
    public Map<String, Object> updatePassword(User user){
        return userService.updatePassword(user);
    }

    @GetMapping("activation")
    public Map<String,Object> activationAccount(String confirmCode){
        return userService.activationAccount(confirmCode);
    }

    @PostMapping("borrow")
    public Map<String, Object> borrowEquipment(Equipment equipment){
        return userService.borrowEquipment(equipment);
    }
    @PostMapping("return")
    public Map<String, Object> returnEquipment(Equipment equipment){
        return userService.returnEquipment(equipment);
    }

}
