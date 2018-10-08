package com.hjh.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.AdWarning;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjh.entity.AdWarningWithPic;
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
public interface AdWarningDao extends BaseMapper<AdWarning> {


    List<AdWarningWithPic> selectMyWarning(Page<AdWarningWithPic> page, @Param("userId") String userId);
    long selectMyWarningCount(@Param("userId") String userId);

    AdWarningWithPic getWarningDetail(@Param("warningId")String warningId);

    List<Map<String,Object>> adMonitor(@Param("companyId")String companyId, @Param("startDate")String startDate, @Param("endDate")String endDate);
}