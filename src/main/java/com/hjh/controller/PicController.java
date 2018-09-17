package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.dao.TUserDao;
import com.hjh.entity.TUser;
import com.hjh.service.IPicFileService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 16:36 2018/9/11
 */
@Controller
@RequestMapping("/pic")
public class PicController extends BaseController{
    @Autowired
    IPicFileService iPicFileService;
    @Autowired
    TUserDao tUserDao;
    @ResponseBody
    @RequestMapping("/test")
    public String  test(){
        //List<TOrder> tOrders = tOrderDao.selectList(new EntityWrapper<TOrder>());
        List<TUser> tUsers = tUserDao.selectList(new EntityWrapper<TUser>());
        return ResultInfoUtils.infoData(tUsers);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file){
        try{
            return iPicFileService.uploadFile(request,file);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
