package com.dylonz.shop.service;

import com.dylonz.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> queryAll();

    Goods insert(Goods goods);

    List<Goods> querynew();

    Goods queryById(Integer id);
}
