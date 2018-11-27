package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.constant.Constant;
import com.hjh.dao.UserCompanyDao;
import com.hjh.entity.Company;
import com.hjh.entity.TUser;
import com.hjh.dao.TUserDao;
import com.hjh.entity.UserCompany;
import com.hjh.service.ITUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUser> implements ITUserService {

    @Autowired
    TUserDao tUserDao;
    @Autowired
    UserCompanyDao userCompanyDao;

    @Override
    public String login(String account, String password, String cid) {
        TUser tUser = new TUser();
        tUser = tUser.selectOne(new EntityWrapper().eq("mobile", account)
                .eq("status", 1));
        if (EmptyUtils.isEmpty(tUser)) {
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (!password.equals(tUser.getPassword())) {
            throw new YqhException(BaseMessageEnum.LOGIN_FAILL);
        }
        if (EmptyUtils.isNotEmpty(cid)) {
            tUser.setCid(cid);
            tUser.updateById();
        }
        tUser.setPassword(null);
        tUser.setCompanyId(getUserCompanyIdsString(tUser.getUserId()));
        List<Map<String, Object>> company_name = userCompanyDao.selectUserCompanyName(tUser.getUserId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, Object> stringObjectMap : company_name) {
            stringBuilder.append(stringObjectMap.get("company_name"));
            stringBuilder.append(",");
        }
        tUser.setCompanyName(stringBuilder.substring(0, stringBuilder.length() - 1));
        return ResultInfoUtils.infoData(tUser);
    }

    @Override
    public String updateUserInfo(String userId, String userName, String path) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        if (EmptyUtils.isNotEmpty(userName)) {
            tUser.setName(userName);
        }
        if (EmptyUtils.isNotEmpty(path)) {
            tUser.setPortraitUrl(path);
        }
        tUser.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String changePassword(String userId, String oldPassword, String newPassword) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (EmptyUtils.isEmpty(tUser)) {
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (!oldPassword.equals(tUser.getPassword())) {
            throw new YqhException(BaseMessageEnum.ERROR_PASSWORD);
        }
        tUser.setPassword(newPassword);
        tUser.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String addUser(String userId, String name, String mobile, String companyId, Integer userType, String password) {
        Integer integer = tUserDao.selectCount(new EntityWrapper<TUser>().eq("status", 1)
                .eq("mobile", mobile));
        if (integer > 0) {
            throw new YqhException(BaseMessageEnum.MOBILE_EXIST);
        }
        TUser t = new TUser();
        t.setUserId(userId);
        t = t.selectById();
        if (EmptyUtils.isEmpty(t)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "用户不存在");
        }
        String superiorUserId = null;
        if (2 == t.getUserType()) {
            superiorUserId = userId;
        }
        TUser tUser = new TUser();
        String createUserId = UUID.randomUUID().toString();
        tUser.setUserId(createUserId);
        tUser.setName(name);
        tUser.setMobile(mobile);
        if (EmptyUtils.isNotEmpty(superiorUserId)) {
            tUser.setSuperiorUserId(superiorUserId);
        }
        tUser.setPassword(password);
        tUser.setUserType(userType);
        tUser.setStatus(1);
        tUser.insert();
        String[] cs = companyId.split(",");
        for (String c : cs) {
            addUserToCompany(createUserId, c);
        }
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String updateUser(String userId, String name, String mobile, String companyId, Integer userType, String password) {
//        if (1==userType) {
//            TUser superior = new TUser();
//            superior.setUserId(superiorUserId);
//            superior = superior.selectById();
//            if (2 != superior.getUserType()) {
//                throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "上级不是监管者，请重新选择");
//            }
//            companyId=superior.getCompanyId();
//        }
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser.setName(name);
        tUser.setMobile(mobile);
        //tUser.setCompanyId(companyId);
        if (EmptyUtils.isNotEmpty(password)) {
            tUser.setPassword(password);
        }
        tUser.setUserType(userType);
        tUser.updateById();
        userCompanyDao.delete(new EntityWrapper<UserCompany>().eq("user_id", userId));
        String[] split = companyId.split(",");
        for (String s : split) {
            addUserToCompany(userId, s);
        }
        return ResultInfoUtils.infoData();
    }

    @Override
    public String addUserToCompany(String userId, String companyId) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        Company company = new Company();
        company.setCompanyId(companyId);
        company = company.selectById();
        if (EmptyUtils.isEmpty(tUser)) {
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (EmptyUtils.isEmpty(company)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "公司不存在");
        }
        Integer integer = userCompanyDao.selectCount(new EntityWrapper<UserCompany>().eq("user_id", userId)
                .eq("company_id", companyId)
                .eq("status", Constant.STATUS_NORMAL));
        if (integer > 0) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "用户已在该公司");
        }
        UserCompany userCompany = new UserCompany();
        userCompany.setUserCompanyId(UUID.randomUUID().toString());
        userCompany.setUserId(userId);
        userCompany.setCompanyId(companyId);
        userCompany.setStatus(Constant.STATUS_NORMAL);
        userCompany.insert();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String removeUserCompany(String userCompanyId) {
        UserCompany uc = new UserCompany();
        uc.setUserCompanyId(userCompanyId);
        uc.setStatus(Constant.STATUS_NORMAL);
        UserCompany userCompany = userCompanyDao.selectOne(uc);
        if (EmptyUtils.isEmpty(userCompany)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "用户不在该公司");
        }
        userCompany.setStatus(Constant.STATUS_DELETE);
        userCompany.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String selectUserCompany(String userId, String companyId, String keyWord) {
        List<Map<String, Object>> maps = userCompanyDao.selectUserCompany(userId, companyId, keyWord);
        return ResultInfoUtils.infoData(maps);
    }

    @Override
    public String getUserCompanyIdsString(String userId) {
        List<UserCompany> userCompanies = userCompanyDao.selectList(new EntityWrapper<UserCompany>()
                .eq("user_id", userId)
                .eq("status", 1));
        StringBuilder stringBuilder = new StringBuilder();
        if (EmptyUtils.isEmpty(userCompanies)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "用户未绑定公司");
        }
        for (UserCompany userCompany : userCompanies) {
            stringBuilder.append(userCompany.getCompanyId());
            stringBuilder.append(",");
        }
        String companyIds = stringBuilder.substring(0, stringBuilder.length() - 1);
        return companyIds;
    }

    @Override
    public String deleteUser(String userId, String deleteUserId) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (2 != tUser.getUserType() && 3 != tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "操作用户不为监管者或者管理员");
        }
        for (String s : deleteUserId.split(",")) {
            if (EmptyUtils.isEmpty(s))
                continue;
            TUser delUser = new TUser();
            delUser.setUserId(s);
            delUser.setStatus(-1);
            delUser.updateById();
        }
        return ResultInfoUtils.infoData();
    }

}
