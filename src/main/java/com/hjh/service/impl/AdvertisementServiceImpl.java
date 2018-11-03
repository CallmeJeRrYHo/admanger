package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.constant.Constant;
import com.hjh.dao.PicFileDao;
import com.hjh.entity.Advertisement;
import com.hjh.dao.AdvertisementDao;
import com.hjh.entity.MyAd;
import com.hjh.entity.PicFile;
import com.hjh.entity.TUser;
import com.hjh.service.IAdvertisementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementDao, Advertisement> implements IAdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao;
    @Autowired
    PicFileDao picFileDao;

    @Override
    public String selectMyAd(String userId, Integer adType, Integer adSpec, Integer adStatus, Integer index, Integer pageSize) {
        TUser user=new TUser();
        user.setUserId(userId);
        user=user.selectById();
        if (EmptyUtils.isEmpty(user)){
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        String isOnlyMy=null;
        if (1==user.getUserType()){
            isOnlyMy="1";
        }
        if (3==user.getUserType()){
            userId=null;
        }
        List<Map<String, Object>> myAds = advertisementDao.selectMyAd(new Page<MyAd>(index, pageSize), userId, adType, adSpec, adStatus,isOnlyMy);
        return ResultInfoUtils.infoData(myAds);
    }

    @Override
    public String deleteAd(String userId, String advertisementId) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setStatus(Constant.STATUS_DELETE);
        Integer update = advertisementDao.update(advertisement, new EntityWrapper<Advertisement>()
                .eq("advertisement_id", advertisementId)
                .eq("status", Constant.STATUS_NORMAL));
        if (update < 1) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR, "删除错误");
        }
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String addAdvertisement(String userId, String companyId, String serialNum, Double lontitude, Double latitude, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String pic, String address, String nearCamera, String nearPolice,String monitorUserId) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        Advertisement advertisement = new Advertisement();
        String id = UUID.randomUUID().toString();
        advertisement.setAdvertisementId(id);
        advertisement.setUserId(userId);
        advertisement.setCompanyId(companyId);
        advertisement.setSerialNum(serialNum);
        advertisement.setMonitorUserId(monitorUserId);
        advertisement.setLontitude(BigDecimal.valueOf(lontitude));
        advertisement.setLatitude(BigDecimal.valueOf(latitude));
        advertisement.setAdType(adType);
        if (EmptyUtils.isNotEmpty(nearCamera))
            advertisement.setNearCamera(nearCamera);
        if (EmptyUtils.isNotEmpty(nearPolice))
            advertisement.setNearPolice(nearPolice);
        advertisement.setAdSpec(adSpec);
        advertisement.setHasLeaderPortrait(hasLeaderPortrait);
        advertisement.setAdContent(adContent);
        advertisement.setAddress(address);
        advertisement.setAdStatus(Constant.AD_STATUS_WAIT_AUDIT);
        advertisement.setStatus(Constant.STATUS_NORMAL);
        advertisement.insert();
        JSONArray jsonArray = JSONArray.fromObject(pic);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PicFile picFile = new PicFile();
            picFile.setPicId(jsonObject.getString("pic_id"));
            picFile.setPath(jsonObject.getString("path"));
            picFile.setType(Constant.PIC_DESIGN_PIC);
            picFile.setStatus(Constant.STATUS_NORMAL);
            picFile.setBusnessId(id);
            picFile.insert();
        }
        return ResultInfoUtils.infoData(id);
    }

    /**
     * 查看是否监管者
     */
    public void checkIsMonitor(String userId) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (2 != tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "你不是监管者");
        }
    }

    @Override
    public String designAudit(String userId, String advertisementId, String auditResponse, Integer auditStatus) {
        checkIsMonitor(userId);
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement = advertisement.selectById();
        Integer adStatus = advertisement.getAdStatus();
        if (Constant.AD_STATUS_WAIT_AUDIT != adStatus && Constant.AD_STATUS_DESIGN_NOT_PASS != adStatus) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR, "订单状态有误");
        }
        if (1 == auditStatus) {
            advertisement.setAdStatus(Constant.AD_STATUS_DESIGN_PASS);
        } else if (2 == auditStatus) {
            advertisement.setAuditResponse(auditResponse);
            advertisement.setAdStatus(Constant.AD_STATUS_DESIGN_NOT_PASS);
        }
        advertisement.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String submitLiveViewAudit(String userId, String advertisementId, String pic) {
        checkAdIsBelongUser(userId, advertisementId);
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setAdStatus(Constant.AD_STATUS_WAIT_AUDIT_LIVE_VIEW);
        advertisement.updateById();
        JSONArray jsonArray = JSONArray.fromObject(pic);
        boolean isFirstSet=false;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PicFile picFile = new PicFile();
            picFile.setPicId(jsonObject.getString("pic_id"));
            if (jsonObject.has("is_first")&&!isFirstSet) {
                picFile.setIs_first(jsonObject.getInt("is_first"));
                if (1==jsonObject.getInt("is_first")) {
                    isFirstSet = true;
                }
            }else {
                picFile.setIs_first(0);

            }
            picFile.setPath(jsonObject.getString("path"));
            picFile.setType(Constant.PIC_LIVE_VIEW_PIC);
            picFile.setStatus(Constant.STATUS_NORMAL);
            picFile.setBusnessId(advertisementId);
            picFile.insert();
        }
        return ResultInfoUtils.infoData();
    }

    private void checkAdIsBelongUser(String userId, String advertisementId) {
        Integer integer = advertisementDao.selectCount(new EntityWrapper<Advertisement>().eq("user_id", userId)
                .eq("advertisement_id", advertisementId)
                .eq("ad_status", Constant.AD_STATUS_DESIGN_PASS)
                .eq("status", Constant.STATUS_NORMAL));
        if (integer < 1) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "该广告不属于该用户");
        }
    }

    @Override
    public String auditLiveView(String userId, String advertisementId) {
        checkIsMonitor(userId);
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement = advertisement.selectById();
        if (4 != advertisement.getAdStatus()) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR, "订单状态有误");
        }
        advertisement.setAdStatus(Constant.AD_STATUS_ALL_PASS);
        advertisement.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String updateDesignAudit(String userId, String advertisementId, String pic) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setUserId(userId);
        advertisement = advertisement.selectById();
        if (EmptyUtils.isEmpty(advertisement)) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR, "您不是该广告的创建者");
        }
        if (Constant.AD_STATUS_DESIGN_NOT_PASS != advertisement.getAdStatus()) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR, "广告状态有误");
        }
        picFileDao.delete(new EntityWrapper<PicFile>()
                .eq("busness_id", advertisementId)
                .eq("status", Constant.STATUS_NORMAL)
                .eq("type", Constant.PIC_DESIGN_PIC));
        JSONArray jsonArray = JSONArray.fromObject(pic);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PicFile picFile = new PicFile();
            picFile.setPicId(jsonObject.getString("pic_id"));
            picFile.setPath(jsonObject.getString("path"));
            picFile.setStatus(Constant.STATUS_NORMAL);
            picFile.setType(Constant.PIC_DESIGN_PIC);
            picFile.setBusnessId(advertisementId);
            picFile.insertOrUpdate();
        }
        advertisement.setAdStatus(Constant.AD_STATUS_WAIT_AUDIT);
        advertisement.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateDesign(String userId, String advertisementId, String serialNum, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String designPic, String address, Double lontitude, Double latitude, String monitorUserId, String nearCamera, String nearPolice) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement = advertisement.selectById();
        if (!advertisement.getUserId().equals(userId)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "你不是创建者");
        }
        if (Constant.AD_STATUS_DESIGN_NOT_PASS != advertisement.getAdStatus()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "广告状态有误，请刷新");
        }
        advertisement.setMonitorUserId(monitorUserId);
        advertisement.setSerialNum(serialNum);
        advertisement.setAdType(adType);
        advertisement.setAdSpec(adSpec);
        advertisement.setHasLeaderPortrait(hasLeaderPortrait);
        if (EmptyUtils.isNotEmpty(address))
            advertisement.setAddress(address);
        if (EmptyUtils.isNotEmpty(adContent))
            advertisement.setAdContent(adContent);
        if (EmptyUtils.isNotEmpty(lontitude))
            advertisement.setLontitude(BigDecimal.valueOf(lontitude));
        if (EmptyUtils.isNotEmpty(latitude))
            advertisement.setLatitude(BigDecimal.valueOf(latitude));
        if (EmptyUtils.isNotEmpty(nearPolice))
            advertisement.setNearPolice(nearPolice);
        if (EmptyUtils.isNotEmpty(nearCamera))
            advertisement.setNearPolice(nearCamera);
        advertisement.setAdStatus(Constant.AD_STATUS_WAIT_AUDIT);
        advertisement.updateById();
        if (EmptyUtils.isNotEmpty(designPic)) {
            picFileDao.delete(new EntityWrapper<PicFile>().eq("busness_id", advertisementId)
                    .eq("type", Constant.PIC_DESIGN_PIC));
            JSONArray jsonArray = JSONArray.fromObject(designPic);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PicFile picFile = new PicFile();
                picFile.setPicId(jsonObject.getString("pic_id"));
                picFile.setPath(jsonObject.getString("path"));
                picFile.setType(Constant.PIC_DESIGN_PIC);
                picFile.setStatus(Constant.STATUS_NORMAL);
                picFile.setBusnessId(advertisementId);
                picFile.insert();
            }
        }
        return ResultInfoUtils.infoData();
    }

    @Override
    public String adStatistics(String companyId, Integer type) {
        List<Map<String, Object>> maps = advertisementDao.adStatistics(companyId,type);
        return ResultInfoUtils.infoData(maps);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateAdvertisement(String userId, String advertisementId, String pic, String companyId,
                                      String serialNum, Double lontitude, Double latitude, Integer adType,
                                      Integer adSpec, Integer hasLeaderPortrait, String adContent, String address,
                                      String nearCamera, String nearPolice, String monitorUserId) {
        Advertisement advertisement=new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        if (EmptyUtils.isNotEmpty(companyId)) {
            advertisement.setCompanyId(companyId);
        }
        if (EmptyUtils.isNotEmpty(serialNum)) {
            advertisement.setSerialNum(serialNum);
        }
        if (EmptyUtils.isNotEmpty(lontitude)) {
            advertisement.setLontitude(BigDecimal.valueOf(lontitude));
        }
        if (EmptyUtils.isNotEmpty(latitude)) {
            advertisement.setLatitude(BigDecimal.valueOf(latitude));
        }
        if (EmptyUtils.isNotEmpty(adType)) {
            advertisement.setAdType(adType);
        }
        if (EmptyUtils.isNotEmpty(adSpec)) {
            advertisement.setAdSpec(adSpec);
        }
        if (EmptyUtils.isNotEmpty(hasLeaderPortrait)) {
            advertisement.setHasLeaderPortrait(hasLeaderPortrait);
        }
        if (EmptyUtils.isNotEmpty(adContent)) {
            advertisement.setAdContent(adContent);
        }
        if (EmptyUtils.isNotEmpty(address)) {
            advertisement.setAddress(address);
        }
        if (EmptyUtils.isNotEmpty(nearCamera)) {
            advertisement.setNearCamera(nearCamera);
        }
        if (EmptyUtils.isNotEmpty(nearPolice)) {
            advertisement.setNearPolice(nearPolice);
        }
        if (EmptyUtils.isNotEmpty(monitorUserId)) {
            advertisement.setMonitorUserId(monitorUserId);
        }
        advertisement.updateById();
        boolean isFirstSet=false;
        if (EmptyUtils.isNotEmpty(pic)) {
            picFileDao.delete(new EntityWrapper<PicFile>().eq("busness_id", advertisementId)
                    .eq("type", Constant.PIC_LIVE_VIEW_PIC));
            JSONArray jsonArray = JSONArray.fromObject(pic);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PicFile picFile = new PicFile();
                picFile.setPicId(jsonObject.getString("pic_id"));
                if (jsonObject.has("is_first")&&!isFirstSet) {
                    picFile.setIs_first(jsonObject.getInt("is_first"));
                    if (1==jsonObject.getInt("is_first")) {
                        isFirstSet = true;
                    }
                }else {
                    picFile.setIs_first(0);
                }
                picFile.setPath(jsonObject.getString("path"));
                picFile.setType(Constant.PIC_LIVE_VIEW_PIC);
                picFile.setStatus(Constant.STATUS_NORMAL);
                picFile.setBusnessId(advertisementId);
                picFile.insert();
            }
        }
        return ResultInfoUtils.infoData();
    }


}
