package com.dylonz.shop.service;

import com.dylonz.entity.ResultData;
import com.dylonz.entity.User;

public interface IUserService {

    ResultData<User> login(String username, String password);
}
