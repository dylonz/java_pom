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
public class Orderdetils implements Serializable {

    private Integer id;
    private Integer oid;
    private Integer gid;
    private String gname;
    private String ginfo;
    private Double price;
    private Integer gcount;
    private String gimage;

}
