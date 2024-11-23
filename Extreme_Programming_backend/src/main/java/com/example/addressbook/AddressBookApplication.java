package com.example.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.addressbook.dao")
public class AddressBookApplication {

    public static void main(String[] args) {

        SpringApplication.run(AddressBookApplication.class, args);

    }

}
