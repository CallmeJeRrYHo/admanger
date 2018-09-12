package com.hjh.controller;

import com.hjh.dao.TUserDao;
import com.hjh.service.ITUserService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 21:14 2018/9/12
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    ITUserService itUserService;
    @Autowired
    TUserDao tUserDao;
    @RequestMapping("/login")
    @ResponseBody
    public String login(String account,String password){
        try{

        checkNecessaryParameter("account",account);
        checkNecessaryParameter("password",password);

            return itUserService.login(account,password);
        }catch(Exception e){
            return handleError(e);
        }

    }


    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo(String userId){
        try{
            checkNecessaryParameter("userId",userId);
            List<Map<String, Object>> userInfo = tUserDao.getUserInfo(userId);
            if (userInfo.size()<1){
                throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
            }
            return ResultInfoUtils.infoData(userInfo);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(String userId,String userName,String path){
        try{
            checkNecessaryParameter("userId",userId);
            return itUserService.updateUserInfo(userId,userName,path);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(String userId,String oldPassword,String newPassword){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("oldPassword",oldPassword);
            checkNecessaryParameter("newPassword",newPassword);
            return itUserService.changePassword(userId,oldPassword,newPassword);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
