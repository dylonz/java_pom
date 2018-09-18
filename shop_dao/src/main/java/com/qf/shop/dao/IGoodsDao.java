package com.qf.shop.dao;

import com.qf.entity.Goods;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public interface IGoodsDao {

    List<Goods> queryAll();
}
