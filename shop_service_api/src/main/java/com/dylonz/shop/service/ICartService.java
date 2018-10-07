package com.dylonz.shop.service;

import com.dylonz.entity.Cart;
import com.dylonz.entity.User;

import java.util.List;

public interface ICartService {

    List<Cart> queryAllByUid(Integer uid);

    int addCart(Cart cart);

    int deleteCart(Integer id);

    int deleteAllCart(Integer uid);

    List<Cart> getCarts(User user,String carts);

    List<Cart> queryCartByGids(Integer[] gid,Integer uid);
}
