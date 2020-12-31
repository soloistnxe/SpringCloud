package com.example.inspect.entity;

import lombok.Data;

@Data
public class Users {
    private Integer uid;
    private String uname;
    private String upassword;
    private Integer utype;
}
