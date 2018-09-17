package com.hjh.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.dao.MsgDao;
import com.hjh.entity.Msg;
import com.hjh.service.IMsgService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 16:20 2018/9/16
 */
@RestController
@RequestMapping("/msg")
public class MsgController extends BaseController {
    @Autowired
    IMsgService msgService;
    @Autowired
    MsgDao msgDao;
    @RequestMapping("/addMsg")
    public String addMsg(String userId,String content,String pics){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("content",content);

            return msgService.addMsg(userId,content,pics);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/selectMsg")
    public String selectMsg(@RequestParam(defaultValue = "1") Integer index,@RequestParam(defaultValue = "10")Integer pageSize){
        try{
            List<Msg> msgs = msgDao.selectMsg(new Page<Msg>(index,pageSize));
            long l = msgDao.selectMsgCount();
            return ResultInfoUtils.infoData(msgs,l);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
