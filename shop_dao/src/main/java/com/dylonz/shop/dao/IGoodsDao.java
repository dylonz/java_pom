package com.dylonz.shop.dao;

import com.dylonz.entity.Goods;

import java.util.List;

public interface IGoodsDao {

    List<Goods> queryAll();

    int insert(Goods goods);

    List<Goods> querynew();

    Goods queryById(Integer id);
}
