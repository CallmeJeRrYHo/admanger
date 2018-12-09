package com.hjh.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.Advertisement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjh.entity.MyAd;
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
public interface AdvertisementDao extends BaseMapper<Advertisement> {

    List<Map<String,Object>> selectAdList(Page<Map<String,Object>>page,@Param("companyId") String companyId, @Param("adType") Integer adType, @Param("adSpec") Integer adSpec, @Param("keyWord") String keyWord,  @Param("lon")Double lon,@Param("lat") Double lat,@Param("isPatrol")Integer isPatrol);

    List<Map<String,Object>> getAdDetail(@Param("advertisementId")String advertisementId);

    List<Map<String ,Object>> selectMyAd(Page<MyAd> var1, @Param("userId") String userId, @Param("adType") Integer adType, @Param("adSpec") Integer adSpec, @Param("adStatus") Integer adStatus, @Param("isOnlyMy")String isOnlyMy,@Param("key")String key);

    List<Map<String ,Object>> adStatistics(@Param("companyId") String companyId, @Param("type")Integer type);

    Map<String,Object> newAdStatistics(@Param("companyId")String companyId);
}