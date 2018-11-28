package com.hjh.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.TUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface TUserDao extends BaseMapper<TUser> {

    List<TUser> getUserInfo(@Param("userId") String userId);

    List<Map<String, Object>> selectUsers(Page<TUser> tUserPage, @Param("companyId") String companyId, @Param("userType") Integer userType, @Param("keyWord") String keyWord, @Param("supUserId")String supUserId);
    long selectUsersCount(@Param("companyId") String companyId, @Param("userType") Integer userType,@Param("keyWord") String keyWord, @Param("supUserId")String supUserId);


    List<String > selectPushCidByCompanyIds(@Param("ids") List<String > ids);

    List<String > selectPushBossCidByCompanyIds(@Param("ids") List<String > ids);
}