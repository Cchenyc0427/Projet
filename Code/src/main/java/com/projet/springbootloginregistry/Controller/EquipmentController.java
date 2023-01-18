package com.projet.springbootloginregistry.Controller;

import com.projet.springbootloginregistry.mapper.EquipmentMapper;
import com.projet.springbootloginregistry.pojo.Equipment;
import com.projet.springbootloginregistry.pojo.User;
import com.projet.springbootloginregistry.service.EquipmentService;
import com.projet.springbootloginregistry.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("equipment")
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentMapper equipmentMapper;

    @PostMapping("add")
    public Map<String,Object> addEquipment(Equipment equipment){
        return equipmentService.addEquipment(equipment);
    }
    @DeleteMapping("delete")
    public Map<String, Object> deleteEquipment(Equipment equipment){
        return equipmentService.deleteEquipment(equipment);
    }

    @PostMapping("findById")
    public Map<String, Object> selectEquipmentById(int id){
        Map<String,Object> equipmentById = new HashMap<>();
        equipmentById.put("data",equipmentMapper.selectEquipmentById(id));
        List<Equipment> equipmentList  =equipmentMapper.selectEquipmentById(id);
        if(equipmentList == null || equipmentList.isEmpty()) {
            equipmentById.put("code", 400);
            equipmentById.put("message", "equipment not exist");
            return equipmentById;
        }else{

            equipmentById.put("code", 400);
            equipmentById.put("message", "find!");
        }
        return equipmentById;
    }

    @PostMapping("findByName")
    public Map<String, Object> selectEquipmentByName(String name){
        Map<String,Object> equipmentByName = new HashMap<>();
        equipmentByName.put("data",equipmentMapper.selectEquipmentByName(name));
        List<Equipment> equipmentList  =equipmentMapper.selectEquipmentByName(name);
        if(equipmentList == null || equipmentList.isEmpty()) {
            equipmentByName.put("code", 400);
            equipmentByName.put("message", "equipment not exist");
            return equipmentByName;
        }else{

            equipmentByName.put("code", 400);
            equipmentByName.put("message", "find!");
        }
        return equipmentByName;
    }

    @PostMapping("findAll")
    public Map<String, Object> selectAllEquipment(String name){
        Map<String,Object> allEquipment = new HashMap<>();
        allEquipment.put("data",equipmentMapper.selectAllEquipment());
        List<Equipment> equipmentList  =equipmentMapper.selectAllEquipment();
        if(equipmentList == null || equipmentList.isEmpty()) {
            allEquipment.put("code", 400);
            allEquipment.put("message", "equipment not exist");
            return allEquipment;
        }else{

            allEquipment.put("code", 400);
            allEquipment.put("message", "find!");
        }
        return allEquipment;
    }

}
