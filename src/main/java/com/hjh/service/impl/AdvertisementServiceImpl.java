package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.entity.Advertisement;
import com.hjh.dao.AdvertisementDao;
import com.hjh.entity.MyAd;
import com.hjh.entity.PicFile;
import com.hjh.entity.TUser;
import com.hjh.service.IAdvertisementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementDao, Advertisement> implements IAdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao;
    @Override
    public String selectMyAd(String userId, Integer adType, Integer adSpec, Integer index, Integer pageSize) {
        List<MyAd> myAds = advertisementDao.selectMyAd(new Page<MyAd>(index, pageSize), userId, adType, adSpec);
        return ResultInfoUtils.infoData(myAds);
    }

    @Override
    public String deleteAd(String userId, String advertisementId) {
        Advertisement advertisement=new Advertisement();
        advertisement.setUserId(userId);
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setStatus(-1);
        Integer update = advertisementDao.update(advertisement, new EntityWrapper<Advertisement>().eq("user_id", userId).eq("advertisement_id", advertisementId).eq("status", 1));
        if (update<1){
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR,"删除错误");
        }
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String addAdvertisement(String userId, String serial_num, Double lontitude, Double latitude, Integer adType, Integer adSpec, Integer hasLeaderPortrait, String adContent, String pic) {
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();

        Advertisement advertisement=new Advertisement();
        String id = UUID.randomUUID().toString();
        advertisement.setAdvertisementId(id);
        advertisement.setUserId(userId);
        advertisement.setCompanyId(tUser.getCompanyId());
        advertisement.setSerialNum(serial_num);
        advertisement.setLontitude(BigDecimal.valueOf(lontitude));
        advertisement.setLatitude(BigDecimal.valueOf(latitude));
        advertisement.setAdType(adType);
        advertisement.setAdSpec(adSpec);
        advertisement.setHasLeaderPortrait(hasLeaderPortrait);
        advertisement.setAdContent(adContent);
        advertisement.setAdStatus(1);
        advertisement.setStatus(1);
        advertisement.insert();
        JSONArray jsonArray=JSONArray.fromObject(pic);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PicFile picFile=new PicFile();
            picFile.setPicId(jsonObject.getString("pic_id"));
            picFile.setPath(jsonObject.getString("path"));
            picFile.setType(2);
            picFile.setBusnessId(id);
            picFile.insert();
        }
        return ResultInfoUtils.infoData();
    }

    /**
     * 查看是否监管者
     */
    public void checkIsMonitor(String userId){
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (2!=tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"你不是监管者");
        }
    }
    @Override
    public String designAudit(String userId, String advertisementId, String auditResponse, Integer auditStatus) {
        checkIsMonitor(userId);
        Advertisement advertisement=new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement=advertisement.selectById();
        Integer adStatus = advertisement.getAdStatus();
        if (1!= adStatus&&2!=adStatus) {
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR,"订单状态有误");
        }
        if (1==auditStatus){
            advertisement.setAdStatus(3);
        }else if (2==auditStatus){
            advertisement.setAuditResponse(auditResponse);
            advertisement.setAdStatus(2);
        }
        advertisement.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String submitLiveViewAudit(String userId, String advertisementId, String pic) {
        checkAdIsBelongUser(userId,advertisementId);
        Advertisement advertisement=new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement.setAdStatus(4);
        advertisement.updateById();
        JSONArray jsonArray=JSONArray.fromObject(pic);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PicFile picFile=new PicFile();
            picFile.setPicId(jsonObject.getString("pic_id"));
            picFile.setPath(jsonObject.getString("path"));
            picFile.setType(3);
            picFile.setBusnessId(advertisementId);
            picFile.insert();
        }
        return ResultInfoUtils.infoData();
    }

    private void checkAdIsBelongUser(String userId, String advertisementId) {
        Integer integer = advertisementDao.selectCount(new EntityWrapper<Advertisement>().eq("user_id", userId)
                .eq("advertisement_id", advertisementId)
                .eq("ad_status", 3)
                .eq("status", 1));
        if (integer<1){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"该广告不属于该用户");
        }
    }

    @Override
    public String auditLiveView(String userId, String advertisementId) {
        checkIsMonitor(userId);
        Advertisement advertisement=new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement=advertisement.selectById();
        if (4!=advertisement.getAdStatus()){
            throw new YqhException(BaseMessageEnum.UPDATE_ERROR,"订单状态有误");
        }
        advertisement.setAdStatus(5);
        advertisement.updateById();
        return ResultInfoUtils.infoData();
    }


}
