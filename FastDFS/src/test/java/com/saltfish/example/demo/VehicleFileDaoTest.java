package com.saltfish.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class VehicleFileDaoTest {
    @Autowired
    VehicleFileDao dao;
    @Test
    void getInfo() {
        if (dao.deleteVehicleFile("askdjbkdfhskfksdhfsfk")){
            System.out.println("asdaksjfhkja askjdhaskjdha khaksdhkashdkashd ");
        }else {
            System.out.println("asjsdkjfha f fa fjhsd fhsk fhskjjd hdajldjfhal h");
        }




    }
}