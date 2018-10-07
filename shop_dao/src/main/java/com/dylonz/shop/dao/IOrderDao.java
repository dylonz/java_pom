package com.dylonz.shop.dao;

import com.dylonz.entity.Orderdetils;
import com.dylonz.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderDao {

    int addOrder(Orders orders);

    int addOrderDetils(@Param("orderdetils") List<Orderdetils> orderdetils);

    List<Orders> queryByUid(Integer uid);

    Orders queryByOrderId(String orderid);

    int updateStatusByOrderid(@Param("orderid") String orderid,@Param("status") Integer status);
}
