package com.example.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor      //生成无参构造方法
public class User_information {


    private String user_name;   //用户名
    private String password;   //密码
    private String user_id;    //身份证号


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
