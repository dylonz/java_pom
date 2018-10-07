package com.dylonz.shop.service;

import com.dylonz.entity.Orders;

import java.util.List;

public interface IOrderService {

    String addOrdersAndOrderdetils(Integer[] cids,Integer aid,Integer uid);

    List<Orders> queryByUid(Integer uid);

    Orders queryByOrderId(String orderid);

    int updateStatusByOrderid(String orderid,Integer status);
}
