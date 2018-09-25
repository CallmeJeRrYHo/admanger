package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.entity.TUser;
import com.hjh.dao.TUserDao;
import com.hjh.service.ITUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.EmptyUtils;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUser> implements ITUserService {

    @Autowired
    TUserDao tUserDao;
    @Override
    public String login(String account, String password) {
        TUser tUser =new TUser();
        tUser=tUser.selectOne(new EntityWrapper().eq("mobile",account)
        .eq("status",1));
        if (EmptyUtils.isEmpty(tUser)){
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (!password.equals(tUser.getPassword())){
            throw new YqhException(BaseMessageEnum.LOGIN_FAILL);
        }
        tUser.setPassword(null);
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
        tUser=tUser.selectById();
        if (EmptyUtils.isEmpty(tUser)){
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (!oldPassword.equals(tUser.getPassword())){
            throw new YqhException(BaseMessageEnum.ERROR_PASSWORD);
        }
        tUser.setPassword(newPassword);
        tUser.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String addUser(String name, String mobile, String superiorUserId, String companyId,  Integer userType, String password) {
        if (1==userType) {
            TUser superior = new TUser();
            superior.setUserId(superiorUserId);
            superior = superior.selectById();
            if (2 != superior.getUserType()) {
                throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "上级不是监管者，请重新选择");
            }
            companyId=superior.getCompanyId();
        }
        Integer integer = tUserDao.selectCount(new EntityWrapper<TUser>().eq("status", 1)
                .eq("mobile", mobile));
        if (integer>0){
            throw new YqhException(BaseMessageEnum.MOBILE_EXIST);
        }
        TUser tUser=new TUser();
        tUser.setUserId(UUID.randomUUID().toString());
        tUser.setName(name);
        tUser.setMobile(mobile);
        tUser.setSuperiorUserId(superiorUserId);
        tUser.setCompanyId(companyId);
        tUser.setPassword(password);
        tUser.setUserType(userType);
        tUser.setStatus(1);
        tUser.insert();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String updateUser(String userId, String name, String mobile, String superiorUserId, String companyId, Integer userType, String password) {
        if (1==userType) {
            TUser superior = new TUser();
            superior.setUserId(superiorUserId);
            superior = superior.selectById();
            if (2 != superior.getUserType()) {
                throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "上级不是监管者，请重新选择");
            }
            companyId=superior.getCompanyId();
        }
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser.setName(name);
        tUser.setMobile(mobile);
        tUser.setSuperiorUserId(superiorUserId);
        tUser.setCompanyId(companyId);
        if (EmptyUtils.isNotEmpty(password)) {
            tUser.setPassword(password);
        }
        tUser.setUserType(userType);
        tUser.updateById();
        return ResultInfoUtils.infoData();
    }
    @Override
    public String deleteUser(String userId, String deleteUserId) {
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (2!=tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"操作用户不为监管者");
        }
        TUser delUser=new TUser();
        delUser.setUserId(deleteUserId);
        delUser.setStatus(-1);
        delUser.updateById();
        return ResultInfoUtils.infoData();
    }

}
