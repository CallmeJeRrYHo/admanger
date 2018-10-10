package com.hjh.service;

import com.hjh.entity.AdWarning;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface IAdWarningService extends IService<AdWarning> {

    String sendWarning(String advertisementId, String userId, String content, String pics);

    String handleWarning(String content, String userId, String warningId);

    String auditWarningHandle(String userId, String warningId, Integer auditStatus);

    void readWarning(String warningId, String userId);
}
