package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.dao.CompanyDao;
import com.hjh.dao.TUserDao;
import com.hjh.entity.Company;
import com.hjh.service.ICompanyService;
import com.hjh.service.ITUserService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    ICompanyService iCompanyService;
    @Autowired
    CompanyDao companyDao;
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

    @RequestMapping("/addCompany")
    @ResponseBody
    public String addCompany(String userId,String companyName){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("companyName",companyName);
            return iCompanyService.addCompany(userId,companyName);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @RequestMapping("/selectCompany")
    @ResponseBody
    public String selectCompany(@RequestParam(defaultValue = "1")Integer index,@RequestParam(defaultValue = "10")Integer pageSize){
        try{
            Company company = new Company();
            Page<Company> status = company.selectPage(new Page<Company>(index, pageSize), new EntityWrapper<Company>().eq("status", 1));
            return ResultInfoUtils.infoData(status.getRecords(),status.getTotal());
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/updateCompany")
    @ResponseBody
    public String updateCompany(String userId,String companyId,String companyName){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("companyName",companyName);
            checkNecessaryParameter("companyId",companyId);
            return iCompanyService.updateCompany(userId,companyId,companyName);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
