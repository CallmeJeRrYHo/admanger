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
    public String login(String account, String password) {
        try {

            checkNecessaryParameter("账号", account);
            checkNecessaryParameter("密码", password);

            return itUserService.login(account, password);
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
            Company company = new Company();
            Wrapper<Company> wrapper = new EntityWrapper<Company>()
                    .eq("status", Constant.STATUS_NORMAL)
                    .orderBy("create_time", false);
            if (EmptyUtils.isNotEmpty(keyWord)){
                wrapper=wrapper.like("company_name","%"+keyWord+"%");
            }
            Page<Company> status = company.selectPage(new Page<Company>(index, pageSize), wrapper);
            return ResultInfoUtils.infoData(status.getRecords(), status.getTotal());
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
    public String addUser(String name, String mobile, String superiorUserId, String companyId, Integer userType, String password) {
        try {
            checkNecessaryParameter("名字", name);
            checkNecessaryParameter("手机号", mobile);
            checkNecessaryParameter("用户类型", userType);
//            if (1 == userType) {
//                checkNecessaryParameter("上级", superiorUserId);
//            } else {
                checkNecessaryParameter("公司", companyId);
//            }
            checkNecessaryParameter("密码", password);
            return itUserService.addUser(name, mobile, superiorUserId, companyId, userType, password);
        } catch (Exception e) {
            return handleError(e);
        }

    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(String userId, String name, String mobile, String superiorUserId, String companyId, Integer userType, String password) {
        try {
            checkNecessaryParameter("userId", userId);
            return itUserService.updateUser(userId, name, mobile, superiorUserId, companyId, userType, password);
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
    public String selectUsers(String keyWord,String companyId, Integer userType, @RequestParam(defaultValue = "1") Integer index, @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            List<Map<String, Object>> users = tUserDao.selectUsers(new Page<TUser>(index, pageSize), companyId, userType,keyWord);
            long l = tUserDao.selectUsersCount(companyId, userType,keyWord);
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
}
