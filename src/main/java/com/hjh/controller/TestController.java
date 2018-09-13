package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.dao.TUserDao;
import com.hjh.entity.TUser;
import com.yqh.util.common.ResultInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 16:36 2018/9/11
 */
@Controller
@RequestMapping("/aaa")
public class TestController {

    @Autowired
    TUserDao tUserDao;
    @ResponseBody
    @RequestMapping("/test")
    public String  test(){
        //List<TOrder> tOrders = tOrderDao.selectList(new EntityWrapper<TOrder>());
        List<TUser> tUsers = tUserDao.selectList(new EntityWrapper<TUser>());
        return ResultInfoUtils.infoData(tUsers);
    }
}
