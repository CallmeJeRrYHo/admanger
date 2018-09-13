package com.hjh.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.JsonArray;
import com.hjh.entity.AdWarning;
import com.hjh.dao.AdWarningDao;
import com.hjh.entity.PicFile;
import com.hjh.service.IAdWarningService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.EmptyUtils;
import com.yqh.util.common.JsonUtil;
import com.yqh.util.common.ResultInfoUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

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
public class AdWarningServiceImpl extends ServiceImpl<AdWarningDao, AdWarning> implements IAdWarningService {


    @Override
    public String sendWarning(String advertisementId, String userId, String content, String pics) {
        AdWarning adWarning=new AdWarning();
        String warningId = UUID.randomUUID().toString();
        adWarning.setWarningId(warningId);
        adWarning.setAdvertisememtId(advertisementId);
        adWarning.setUserId(userId);
        adWarning.setWarningStatus(1);
        adWarning.setContent(content);
        adWarning.insert();
        if (EmptyUtils.isNotEmpty(pics)){
            JSONArray jsonArray = JSONArray.fromObject(pics);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject x = jsonArray.getJSONObject(i);
                PicFile picFile=new PicFile();
                picFile.setPicId(UUID.randomUUID().toString());
                picFile.setPath(x.getString("path"));
                picFile.setType(4);
                picFile.setBusnessId(warningId);
                picFile.insert();
            }
        }
         return ResultInfoUtils.infoData();
    }
}
