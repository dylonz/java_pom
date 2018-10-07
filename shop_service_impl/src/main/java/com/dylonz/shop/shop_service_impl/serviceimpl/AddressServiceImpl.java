package com.dylonz.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.Address;
import com.dylonz.shop.dao.IAddressDao;
import com.dylonz.shop.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressDao addressDao;

    @Override
    public List<Address> queryByUid(Integer uid) {

        return addressDao.queryByUid(uid);
    }

    @Override
    public Address addAddress(Address address) {
        addressDao.addAddress(address);
        return address;
    }
}
