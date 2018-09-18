package com.hjh.dao;

import com.hjh.entity.PicFile;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<PicFile> selectPicForAd(@Param("advertisement_id")String msg_id);
}