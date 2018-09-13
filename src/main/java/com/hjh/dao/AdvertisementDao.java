package com.hjh.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.Advertisement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjh.entity.MyAd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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

    List<Map<String,Object>> selectAdList(@Param("companyId") String companyId, @Param("adType") Integer adType, @Param("adSpec") Integer adSpec, @Param("keyWord")String keyWord, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    List<Map<String,Object>> getAdDetail(@Param("advertisementId")String advertisementId);

    List<MyAd> selectMyAd(Page<MyAd> var1, @Param("userId")String userId, @Param("adType")Integer adType, @Param("adSpec")Integer adSpec);
}