package com.hjh.controller;

import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.constant.Constant;
import com.hjh.dao.AdWarningDao;
import com.hjh.dao.CompanyDao;
import com.hjh.dao.TUserDao;
import com.hjh.dao.UserCompanyDao;
import com.hjh.entity.Company;
import com.hjh.entity.TUser;
import com.hjh.entity.UserCompany;
import com.hjh.service.ICompanyService;
import com.hjh.service.ITUserService;
import com.hjh.utils.BaseController;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    @Autowired
    AdWarningDao adWarningDao;
    @Autowired
    private UserCompanyDao userCompanyDao;
    @RequestMapping("/login")
    @ResponseBody
    public String login(String account, String password,String cid) {
        try {

            checkNecessaryParameter("账号", account);
            checkNecessaryParameter("密码", password);

            return itUserService.login(account, password,cid);
        } catch (Exception e) {
            return handleError(e);
        }

    }


    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getUserInfo(String userId) {
        try {
            checkNecessaryParameter("userId", userId);
            List<TUser> userInfo = tUserDao.getUserInfo(userId);
            if (userInfo.size() < 1) {
                throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
            }
            userInfo.get(0).setCompanyId(itUserService.getUserCompanyIdsString(userId));
            List<Map<String, Object>> company_name = userCompanyDao.selectUserCompanyName(userId);
            StringBuilder stringBuilder=new StringBuilder();
            for (Map<String, Object> stringObjectMap : company_name) {
                stringBuilder.append(stringObjectMap.get("company_name"));
                stringBuilder.append(",");
            }
            userInfo.get(0).setCompanyName(stringBuilder.substring(0,stringBuilder.length()-1));
            return ResultInfoUtils.infoData(userInfo);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(String userId, String userName, String path, String oldPassword, String newPassword) {
        try {
            checkNecessaryParameter("userId", userId);
            if (EmptyUtils.isNotEmpty(oldPassword) && EmptyUtils.isNotEmpty(newPassword)) {
                itUserService.changePassword(userId, oldPassword, newPassword);
            }
            return itUserService.updateUserInfo(userId, userName, path);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(String userId, String oldPassword, String newPassword) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("旧密码", oldPassword);
            checkNecessaryParameter("新密码", newPassword);
            return itUserService.changePassword(userId, oldPassword, newPassword);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/addCompany")
    @ResponseBody
    public String addCompany(String userId, String companyName) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("companyName", companyName);
            return iCompanyService.addCompany(userId, companyName);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/selectCompany")
    @ResponseBody
    public String selectCompany(@RequestParam(defaultValue = "1") Integer index, @RequestParam(defaultValue = "10") Integer pageSize,String keyWord) {
        try {

            return iCompanyService.selectCompany(index,pageSize,keyWord);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/updateCompany")
    @ResponseBody
    public String updateCompany(String userId, String companyId, String companyName) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("公司名", companyName);
            checkNecessaryParameter("companyId", companyId);
            return iCompanyService.updateCompany(userId, companyId, companyName);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/deleteCompany")
    @ResponseBody
    public String deleteCompany(String userId, String companyId) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("companyId", companyId);
            return iCompanyService.deleteCompany(userId, companyId);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(String userId,String name, String mobile,String companyId, Integer userType, String password) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("名字", name);
            checkNecessaryParameter("手机号", mobile);
            checkNecessaryParameter("用户类型", userType);
            checkNecessaryParameter("公司", companyId);
            checkNecessaryParameter("密码", password);
            return itUserService.addUser(userId,name, mobile, companyId, userType, password);
        } catch (Exception e) {
            return handleError(e);
        }

    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(String userId, String name, String mobile,  String companyId, Integer userType, String password) {
        try {
            checkNecessaryParameter("userId", userId);
            return itUserService.updateUser(userId, name, mobile,  companyId, userType, password);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(String userId, String deleteUserId) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("deleteUserId", deleteUserId);
            return itUserService.deleteUser(userId, deleteUserId);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/selectUsers")
    @ResponseBody
    public String selectUsers(String userId,String keyWord,String companyId, Integer userType, @RequestParam(defaultValue = "1") Integer index, @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            checkNecessaryParameter("userId",userId);
            TUser tUser=new TUser();
            tUser.setUserId(userId);
            tUser=tUser.selectById();
            if (EmptyUtils.isEmpty(tUser)){
                throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
            }
            String supUserId=null;
            if (2==tUser.getUserType()){
                supUserId=userId;
            }
            List<Map<String, Object>> users = tUserDao.selectUsers(new Page<TUser>(index, pageSize), companyId, userType,keyWord,supUserId);
            long l = tUserDao.selectUsersCount(companyId, userType,keyWord,supUserId);
            return ResultInfoUtils.infoData(users, l);
        } catch (Exception e) {
            return handleError(e);
        }

    }

    @RequestMapping("/addUserToCompany")
    @ResponseBody
    public String addUserToCompany(String userId, String companyId) {
        try {
            checkNecessaryParameter("userId", userId);
            checkNecessaryParameter("companyId", companyId);

            return itUserService.addUserToCompany(userId, companyId);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/removeUserCompany")
    @ResponseBody
    public String removeUserCompany(String userCompanyId) {
        try {
            checkNecessaryParameter("userCompanyId", userCompanyId);


            return itUserService.removeUserCompany(userCompanyId);
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @RequestMapping("/selectUserCompany")
    @ResponseBody
    public String selectUserCompany(String userId, String companyId, String keyWord) {
        try {
            return itUserService.selectUserCompany(userId, companyId, keyWord);
        } catch (Exception e) {
            return handleError(e);
        }
    }


    @RequestMapping("/adMonitor")
    @ResponseBody
    public String adMonitor(String companyId, String startDate, String endDate) {
        try {

            List<Map<String, Object>> maps = adWarningDao.adMonitor(companyId, startDate, endDate);
            return ResultInfoUtils.infoData(maps);
        } catch (Exception e) {
            return handleError(e);
        }
    }


    @RequestMapping("/selectMyCompany")
    @ResponseBody
    public String selectMyCompany(@RequestParam(defaultValue = "1") Integer index, @RequestParam(defaultValue = "10") Integer pageSize,String userId, String companyId, String keyWord){
        try{
            checkNecessaryParameter("userId",userId);

            return iCompanyService.selectMyCompany(index,pageSize,userId,companyId,keyWord);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(String companyId){
        try{

            List<String> ids=new ArrayList<>();
            ids.add(companyId);
            List<String> aa = tUserDao.selectPushBossCidByCompanyIds(ids);
            System.out.println(aa.toString());
            return ResultInfoUtils.infoData(aa);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
