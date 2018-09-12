package com.hjh.dao;

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

    List<Map<String,Object>> getUserInfo(@Param("userId") String userId);
}