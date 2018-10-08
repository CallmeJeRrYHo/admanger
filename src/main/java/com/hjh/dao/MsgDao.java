package com.hjh.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.Msg;
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
public interface MsgDao extends BaseMapper<Msg> {

    List<Msg> selectMsg(Page<Msg> page,@Param("userId")String userId);
    long selectMsgCount(@Param("userId")String userId);
}