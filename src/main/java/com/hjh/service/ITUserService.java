package com.hjh.service;

import com.hjh.entity.TUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface ITUserService extends IService<TUser> {

    String login(String account, String password);


    String updateUserInfo(String userId, String userName, String path);

    String changePassword(String userId, String oldPassword, String newPassword);
}
