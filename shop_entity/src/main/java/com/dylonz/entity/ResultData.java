package com.dylonz.entity;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ResultData<T> implements Serializable {

    private Integer code;  //结果码。 0代表登录成功、1代表密码错误，2代表帐号错误，3代表其他错误

    private String msg; //返回登录成功与否信息

    private T data;
}
