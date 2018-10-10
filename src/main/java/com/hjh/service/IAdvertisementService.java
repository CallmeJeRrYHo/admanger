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

    String selectMyAd(String userId, Integer adType, Integer adSpec,Integer adStatus, Integer index, Integer pageSize);

    String deleteAd(String userId, String advertisementId);

    String addAdvertisement(String userId,String companyId, String serial_num, Double lontitude, Double latitude, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String pic, String address,String nearCamera,String nearPolice,String monitorUserId);

    String designAudit(String userId, String advertisementId, String auditResponse, Integer auditStatus);

    String submitLiveViewAudit(String userId, String advertisementId, String pic);

    String auditLiveView(String userId, String advertisementId);

    String updateDesignAudit(String userId, String advertisementId, String pic);

    String updateDesign(String userId, String advertisementId, String serialNum, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String designPic, String address, Double lontitude, Double latitude,String monitorUserId);

    String adStatistics(String companyId, Integer type);

    String updateAdvertisement(String userId, String advertisementId, String pic, String companyId, String serialNum, Double lontitude, Double latitude, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String address, String nearCamera, String nearPolice, String monitorUserId);
}
