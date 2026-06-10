package com.zouwx.zouwxaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Tretar
 */
@SpringBootApplication
@MapperScan("com.zouwx.zouwxaicodemother.mapper")
public class ZouwxAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZouwxAiCodeMotherApplication.class, args);
    }

}
