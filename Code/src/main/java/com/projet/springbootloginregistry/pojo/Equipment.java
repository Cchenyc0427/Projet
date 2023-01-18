package com.projet.springbootloginregistry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment implements Serializable {

    private Integer id;
    private String name;
    private String userName;
    private byte isBorrow;

}
