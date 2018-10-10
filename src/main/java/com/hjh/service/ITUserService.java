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

    String addUser(String userId, String name, String mobile, String companyId, Integer userType, String password);

    String deleteUser(String userId, String deleteUserId);

    String updateUser(String userId, String name, String mobile, String companyId, Integer userType, String password);

    String addUserToCompany(String userId, String companyId);

    String removeUserCompany(String userCompanyId);

    String selectUserCompany(String userId, String companyId, String keyWord);

    String getUserCompanyIdsString(String userId);
}
