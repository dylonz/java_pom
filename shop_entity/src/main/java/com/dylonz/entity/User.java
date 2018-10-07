package com.dylonz.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    private Integer id;
    private String username;
    private  String password;
    private  String name;
    private  Integer age;
    private Date birthday;
    private  String idcard;
    private String phone;
    private String email;
    private String token;
    private  String time;

}
