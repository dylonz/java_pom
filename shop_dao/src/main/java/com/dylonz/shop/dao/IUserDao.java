package com.dylonz.shop.dao;

import com.dylonz.entity.User;

public interface IUserDao {

    User queryByUsername(String username);
}
