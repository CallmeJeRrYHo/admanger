package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.dao.TOrderDao;
import com.hjh.entity.TOrder;
import com.hjh.service.ITOrderService;
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
    private TOrderDao tOrderDao;
    @ResponseBody
    @RequestMapping("/test")
    public String  test(){
        //List<TOrder> tOrders = tOrderDao.selectList(new EntityWrapper<TOrder>());
        return "21fdgsdgdsfs";
    }
}
