package com.dylonz.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.Goods;
import com.dylonz.shop.dao.IGoodsDao;
import com.dylonz.shop.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<Goods> queryAll() {
        //System.out.println("调用了提供者的方法");

        return goodsDao.queryAll();
    }

    @Override
    public Goods insert(Goods goods) {
        int insert=goodsDao.insert(goods);
       // System.out.println("服务实现类主键"+goods.getId()+"id---"+insert);
        return goods;
    }

    @Override
    public List<Goods> querynew() {
        return goodsDao.querynew();
    }

    @Override
    public Goods queryById(Integer id) {
        return goodsDao.queryById(id);
    }
}
