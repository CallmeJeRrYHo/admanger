package com.hjh.dao;

import com.hjh.entity.PicFile;
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
public interface PicFileDao extends BaseMapper<PicFile> {

    List<PicFile> selectPicForMsg(@Param("msg_id")String msg_id);

    List<Map<String ,Object>> selectLiveViewPicForAd (@Param("advertisement_id")String advertisement_id);

    List<Map<String ,Object>> selectDesignPicForAd(@Param("advertisement_id")String advertisement_id);

    List<Map<String ,Object>> selectPatrolRecordPic(@Param("patrolRecordId")String patrolRecordId);

}