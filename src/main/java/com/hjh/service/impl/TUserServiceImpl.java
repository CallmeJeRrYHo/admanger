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
    public String changePassword(String userId, String oldPassword, String newPassword) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (!oldPassword.equals(tUser.getPassword())){
            throw new YqhException(BaseMessageEnum.ERROR_PASSWORD);
        }
        tUser.setPassword(newPassword);
        tUser.updateById();
        return ResultInfoUtils.infoData();
    }
}
