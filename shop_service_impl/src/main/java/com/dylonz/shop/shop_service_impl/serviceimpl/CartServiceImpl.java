package com.dylonz.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.Cart;
import com.dylonz.entity.Goods;
import com.dylonz.entity.User;
import com.dylonz.shop.dao.ICartsDao;
import com.dylonz.shop.dao.IGoodsDao;
import com.dylonz.shop.service.ICartService;
import com.dylonz.shop.service.IGoodsService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartsDao cartsDao;

    @Autowired
    private IGoodsService goodsService;

    @Override
    public List<Cart> queryAllByUid(Integer uid) {
        return cartsDao.queryAllByUid(uid);
    }

    @Override
    public int addCart(Cart cart) {
        return cartsDao.addCart(cart);
    }

    @Override
    public int deleteCart(Integer id) {
        return cartsDao.deleteCart(id);
    }

    @Override
    public int deleteAllCart(Integer uid) {
        return cartsDao.deleteCart(uid);
    }

    @Override
    public List<Cart> getCarts(User user, String carts) {
        List<Cart> cartList=null;
        if(user!=null){
            //从数据库获取购物车的数据
            cartList = cartsDao.queryAllByUid(user.getId());
        }else {
            //从cookie获取购物车的数据
            TypeToken<List<Cart>> tt=new TypeToken<List<Cart>>() {};
            cartList= new Gson().fromJson(carts, tt.getType());
        }
        if(cartList!=null){
            for(int i=0;i<cartList.size();i++){
                Goods goods = goodsService.queryById(cartList.get(i).getGid());
                cartList.get(i).setGoods(goods);
            }
        }
        return cartList;
    }

    @Override
    public List<Cart> queryCartByGids(Integer[] gid, Integer uid) {
        List<Cart> cartList = cartsDao.queryCartByGids(gid, uid);
        for (int i = 0; i < cartList.size(); i++) {
            Goods goods = goodsService.queryById(cartList.get(i).getGid());
            cartList.get(i).setGoods(goods);
        }
        return cartList;
    }
}
