package com.projet.springbootloginregistry.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;  //md5+ salt
    private String salt;
    private String confirmCode;
    private LocalDateTime activationTime;
    private Byte isValid;
    private String firstName;
    private String lastName;
    private Byte borrow;
    private Integer idEquipment;

}
