package com.projet.springbootloginregistry.Controller;


import com.projet.springbootloginregistry.pojo.Administrator;
import com.projet.springbootloginregistry.pojo.User;
import com.projet.springbootloginregistry.service.AdministratorService;
import com.projet.springbootloginregistry.service.UserService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("administrator")
public class AdministratorController {

    @Resource
    private AdministratorService administratorService;
    @Resource
    private  UserService userService;

    @DeleteMapping("delete")
    public Map<String, Object> deleteAccount(User user){
        return administratorService.deleteAccount(user);
    }
    @PostMapping("loginAdministrator")
    public Map<String, Object> administratorLogin(Administrator administrator){
        return administratorService.loginAdministrator(administrator);
    }
}
