package com.projet.springbootloginregistry.service;

import com.projet.springbootloginregistry.mapper.EquipmentMapper;
import com.projet.springbootloginregistry.mapper.UserMapper;
import com.projet.springbootloginregistry.pojo.Equipment;
import com.projet.springbootloginregistry.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentService {


    List<Equipment> equipmentList;


    @Resource
    private EquipmentMapper equipmentMapper;

    //add equipment
    public Map<String, Object> addEquipment(Equipment equipment) {
        equipment.setName(equipment.getName());
        equipment.setUserName(null);
        equipment.setIsBorrow((byte) 0);
        int result = 0;
        result =  equipmentMapper.insertEquipment(equipment);
        Map<String, Object> resultMap= new HashMap<>();
        if(result> 0 ){
            resultMap.put("code",200);
            resultMap.put("message","Add");
        }else {
            resultMap.put("code", 400);
            resultMap.put("message", "account exist");
        }
        return resultMap;
    }


    public Map<String, Object> deleteEquipment(Equipment equipment){
        Map<String,Object> resultMap =new HashMap<>();
        List<Equipment> equipmentList = equipmentMapper.selectEquipmentById(equipment.getId());
        if(equipmentList==null || equipmentList.isEmpty()){
            resultMap.put("code",400);
            resultMap.put("message","account not exist or no activation");
            return resultMap;
        }
        Equipment e=equipmentList.get(0);
        int result = 0;
        if(e.getIsBorrow()==0){
            result = equipmentMapper.deleteById(equipment.getId());
        }else{
            resultMap.put("code",400);
            resultMap.put("message","This item has been borrowed");
        }
        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","delete!");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","delete failed");
        }

        return resultMap;
    }

    public Map<String, Object> selectEquipmentById(int id){
        Map<String,Object> resultMap =new HashMap<>();
        equipmentList =equipmentMapper.selectEquipmentById(id);

        if(equipmentList == null || equipmentList.isEmpty()) {
            resultMap.put("code", 400);
            resultMap.put("message", "equipment not exist");
            return resultMap;
        }else{

            resultMap.put("code", 400);
            resultMap.put("message", "find!");
        }

        
        return resultMap;
    }
    public Map<String,Object> selectEquipmentByName(String name){
        Map<String, Object> resultMap = new HashMap<>();
        List<Equipment> equipmentList =equipmentMapper.selectEquipmentByName(name);
        if(equipmentList == null || equipmentList.isEmpty()) {
            resultMap.put("code", 400);
            resultMap.put("message", "equipment not exist");
            return resultMap;
        }else{
            resultMap.put("code", 400);
            resultMap.put("message", "find!");
        }
        return resultMap;
    }





}
