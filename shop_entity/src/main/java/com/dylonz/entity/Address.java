package com.dylonz.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Address implements Serializable{

    private Integer id;
    private String person; //收货人
    private String address;
    private String phone;
    private Integer code;
    private Integer uid;
    private Integer isdefault;

}
