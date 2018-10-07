package com.dylonz.shop.service;

import com.dylonz.entity.Address;

import java.util.List;

public interface IAddressService {

    List<Address> queryByUid(Integer uid);

    Address addAddress(Address address);
}
