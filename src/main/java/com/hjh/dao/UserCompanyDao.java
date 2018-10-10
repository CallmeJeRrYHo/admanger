package com.hjh.dao;

import com.hjh.entity.Company;
import com.hjh.entity.UserCompany;
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
 * @since 2018-10-03
 */
public interface UserCompanyDao extends BaseMapper<UserCompany> {
    List<Map<String,Object>>selectUserCompany(@Param("userId") String userId, @Param("companyId") String companyId, @Param("keyWord")String keyWord);

    List<Company>selectMyCompany(@Param("userId") String userId, @Param("companyId") String companyId, @Param("keyWord")String keyWord);

    List<Map<String,Object>>selectUserCompanyName(@Param("userId")String userId);
}