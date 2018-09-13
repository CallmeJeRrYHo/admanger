package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.entity.Advertisement;
import com.hjh.dao.AdvertisementDao;
import com.hjh.entity.MyAd;
import com.hjh.service.IAdvertisementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<MyAd> myAds = advertisementDao.selectMyAd(userId, adType, adSpec, index, pageSize);

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
}
