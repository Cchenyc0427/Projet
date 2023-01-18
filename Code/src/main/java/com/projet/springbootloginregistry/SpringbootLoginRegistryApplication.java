package com.projet.springbootloginregistry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.projet.springbootloginregistry.mapper")
@SpringBootApplication
public class SpringbootLoginRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLoginRegistryApplication.class, args);
    }

}
