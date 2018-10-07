package com.dylonz.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.ResultData;
import com.dylonz.entity.User;
import com.dylonz.shop.dao.IUserDao;
import com.dylonz.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResultData<User> login(String username, String password) {
        int code;
        String msg;

        User user = userDao.queryByUsername(username);
        if(user!=null){
            if(user.getPassword().equals(password)){
                //登录成功
                code=0;
                msg="登录成功";
            }else{
                //密码错误
                code=1;
                msg="密码错误";
                user=null;
            }
        }else{
            //帐号错误
            code=2;
            msg="帐号错误";
        }
        ResultData<User> resultData=new ResultData<>(code,msg,user);

        return resultData;
    }
}
