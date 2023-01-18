package com.projet.springbootloginregistry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrator implements Serializable {
    private Integer id;
    private String email;
    private String password;  //md5+ salt
    private String name;


}