package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.constant.Constant;
import com.hjh.dao.CompanyDao;
import com.hjh.dao.TUserDao;
import com.hjh.entity.Company;
import com.hjh.entity.TUser;
import com.hjh.service.ICompanyService;
import com.hjh.service.ITUserService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.apache.catalina.User;
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
public class UserController extends MyBaseController {
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

        checkNecessaryParameter("账号",account);
        checkNecessaryParameter("密码",password);

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
            checkNecessaryParameter("旧密码",oldPassword);
            checkNecessaryParameter("新密码",newPassword);
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
            Page<Company> status = company.selectPage(new Page<Company>(index, pageSize), new EntityWrapper<Company>()
                    .eq("status", Constant.STATUS_NORMAL)
                    .orderBy("create_time",false));
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
            checkNecessaryParameter("公司名",companyName);
            checkNecessaryParameter("companyId",companyId);
            return iCompanyService.updateCompany(userId,companyId,companyName);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/deleteCompany")
    @ResponseBody
    public String deleteCompany(String userId,String companyId){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("companyId",companyId);
            return iCompanyService.deleteCompany(userId,companyId);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/addUser")
    @ResponseBody
    public  String addUser(String name,String mobile,String superiorUserId,String companyId,Integer userType,String password){
        try{
            checkNecessaryParameter("名字",name);
            checkNecessaryParameter("companyId",companyId);
            checkNecessaryParameter("手机号",mobile);
            checkNecessaryParameter("用户类型",userType);
            if (1==userType){
                checkNecessaryParameter("上级",superiorUserId);
            }
            checkNecessaryParameter("密码",password);
            return itUserService.addUser(name,mobile,superiorUserId,companyId,userType,password);
        }catch(Exception e){
            return handleError(e);
        }

    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(String userId,String deleteUserId){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("deleteUserId",deleteUserId);
            return itUserService.deleteUser(userId,deleteUserId);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @RequestMapping("/selectUsers")
    @ResponseBody
    public String selectUsers(String companyId,Integer userType,@RequestParam(defaultValue = "1")Integer index,@RequestParam(defaultValue = "10")Integer pageSize){
        try{
            List<Map<String,Object>> users = tUserDao.selectUsers(new Page<TUser>(index, pageSize), companyId, userType);
            long l = tUserDao.selectUsersCount(companyId, userType);
            return ResultInfoUtils.infoData(users,l);
        }catch(Exception e){
            return handleError(e);
        }

    }
}
