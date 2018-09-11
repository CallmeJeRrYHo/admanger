package com.hjh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 16:36 2018/9/11
 */
@Controller
@RequestMapping("/aaa")
public class TestController {

    @ResponseBody
    @RequestMapping("/test")
    public String  test(){
        //List<TOrder> tOrders = tOrderDao.selectList(new EntityWrapper<TOrder>());
        return "21fdgsdgdsfs";
    }
}
