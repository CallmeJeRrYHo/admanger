package com.hjh.service;

import com.hjh.entity.Advertisement;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface IAdvertisementService extends IService<Advertisement> {

    String selectMyAd(String userId, Integer adType, Integer adSpec, Integer index, Integer pageSize);

    String deleteAd(String userId, String advertisementId);

    String addAdvertisement(String userId, String serial_num, Double lontitude, Double latitude, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String pic);

    String designAudit(String userId, String advertisementId, String auditResponse, Integer auditStatus);

    String submitLiveViewAudit(String userId, String advertisementId, String pic);

    String auditLiveView(String userId, String advertisementId);
}