package com.dylonz.shop.dao;

import com.dylonz.entity.Address;

import java.util.List;

public interface IAddressDao {

    List<Address> queryByUid(Integer uid);

    int addAddress(Address address);

    Address queryById(Integer id);
}
