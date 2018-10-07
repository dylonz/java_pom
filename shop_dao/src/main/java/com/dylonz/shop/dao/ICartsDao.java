package com.dylonz.shop.dao;

import com.dylonz.entity.Cart;
import com.dylonz.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartsDao {

    List<Cart> queryAllByUid(Integer uid);

    int addCart(Cart cart);

    int deleteCart(Integer id);

    int deleteAllCart(Integer uid);

    //List<Cart> getCarts(User user, String carts);

    List<Cart> queryCartByGids(@Param("gid") Integer[] gid,@Param("uid") Integer uid);

    List<Cart> queryByCids(@Param("cids") Integer[] cids);
}
