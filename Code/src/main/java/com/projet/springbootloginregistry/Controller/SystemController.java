package com.projet.springbootloginregistry.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("registry")
    public String registry(){
        return "registry";
    }
    @GetMapping("update")
    public String update(){
        return "update";
    }
    @GetMapping("administrator/delete")
    public String delete(){
        return "delete";
    }
    @GetMapping ("loginAdministrator")
    public String administratorLogin(){
        return "loginAdministrator";
    }

    @GetMapping("addEquipment")
    public String addEquipment(){
        return "addEquipment";
    }

    @GetMapping("findEquipmentById")
    public String findEquipmentById(){

        return "findEquipmentById";
    }
    @GetMapping("showAllEquipment")
    public String findAllEquipment(){

        return "showAllEquipment";
    }

    @GetMapping("findEquipmentByName")
    public String findEquipmentByName(){
        return "findEquipmentByName";
    }

    @GetMapping("deleteEquipmentById")
    public String deleteEquipmentById(){
        return "deleteEquipment";
    }

    @GetMapping("borrowEquipment")
    public String borrowEquipment(){
        return "borrowEquipment";
    }

    @GetMapping("returnEquipment")
    public String returnEquipment(){
        return "returnEquipment";
    }


}
