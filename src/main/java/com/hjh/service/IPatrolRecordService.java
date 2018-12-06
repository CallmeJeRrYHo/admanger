package com.hjh.service;

import com.hjh.entity.PatrolRecord;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-12-06
 */
public interface IPatrolRecordService extends IService<PatrolRecord> {

    String  add(String userId, Integer hasProblem, Integer isWarning, String warningNo, String pics, String advertismentId);
}
