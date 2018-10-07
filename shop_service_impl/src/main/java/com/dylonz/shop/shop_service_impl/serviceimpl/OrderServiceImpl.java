package com.dylonz.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.Address;
import com.dylonz.entity.Cart;
import com.dylonz.entity.Orderdetils;
import com.dylonz.entity.Orders;
import com.dylonz.shop.dao.IAddressDao;
import com.dylonz.shop.dao.ICartsDao;
import com.dylonz.shop.dao.IOrderDao;
import com.dylonz.shop.service.IOrderService;
import com.dylonz.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ICartsDao cartsDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private IOrderDao orderDao;

    @Override
    @Transactional
    public String addOrdersAndOrderdetils(Integer[] cids, Integer aid, Integer uid) {
        //1.根据购物车id查询购物车列表
        List<Cart> carts = cartsDao.queryByCids(cids);

        //2.根据地址id获取收货地址详细信息
        Address address = addressDao.queryById(aid);

        //计算下订单总价格
        double allprice=0;
        for(Cart cart:carts){
            allprice+=cart.getGnumber() * cart.getGoods().getPrice();
        }

        //3.根据购物车列表生成订单和订单详情对象
        Orders orders=new Orders();
        orders.setOrderid(UUID.randomUUID().toString());
        orders.setAddress(address.getAddress());
        orders.setCode(address.getCode());
        orders.setOrdertime(new Date());
        orders.setPerson(address.getPerson());
        orders.setPhone(address.getPhone());
        orders.setUid(uid);
        orders.setOprice(allprice);  //总价格
        orders.setStatus(0); //0--未支付，1--已支付，待发货，2--已发货，待收货，3--已收货，待评价，4--评价，完成

        //添加订单
        orderDao.addOrder(orders);

        //订单详情
        List<Orderdetils> orderdetilsList=new ArrayList<>();
        for(Cart cart:carts){
            Orderdetils orderdetils=new Orderdetils();
            orderdetils.setOid(orders.getId());
            orderdetils.setGid(cart.getGid());
            orderdetils.setGname(cart.getGoods().getTitle());
            orderdetils.setGinfo(cart.getGoods().getGinfo());
            orderdetils.setPrice(cart.getGoods().getPrice());
            orderdetils.setGcount(cart.getGnumber());
            orderdetils.setGimage(cart.getGoods().getGimage());
            orderdetilsList.add(orderdetils);
        }
        orderDao.addOrderDetils(orderdetilsList);

        //删除购物车
        for(Cart cart:carts){
            cartsDao.deleteCart(cart.getId());
        }
        return orders.getOrderid();
    }

    @Override
    public List<Orders> queryByUid(Integer uid) {
        return orderDao.queryByUid(uid);
    }

    @Override
    public Orders queryByOrderId(String orderid) {
        return orderDao.queryByOrderId(orderid);
    }

    @Override
    public int updateStatusByOrderid(String orderid, Integer status) {
        return orderDao.updateStatusByOrderid(orderid,status);
    }
}
