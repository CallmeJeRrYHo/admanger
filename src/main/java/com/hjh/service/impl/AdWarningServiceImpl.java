package com.hjh.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.JsonArray;
import com.hjh.constant.Constant;
import com.hjh.dao.WarningHandleDao;
import com.hjh.entity.*;
import com.hjh.dao.AdWarningDao;
import com.hjh.service.IAdWarningService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    WarningHandleDao warningHandleDao;
    @Autowired
    AdWarningDao adWarningDao;
    @Override
    public String sendWarning(String advertisementId, String userId, String content, String pics) {
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();

        Advertisement advertisement=new Advertisement();
        advertisement.setAdvertisementId(advertisementId);
        advertisement=advertisement.selectById();
        if (EmptyUtils.isEmpty(advertisement)) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"广告不存在");
        }

        AdWarning adWarning=new AdWarning();
        String warningId = UUID.randomUUID().toString();
        adWarning.setWarningId(warningId);
        adWarning.setAdvertisememtId(advertisementId);
        adWarning.setUserId(userId);
        adWarning.setHandleUserId(advertisement.getUserId());
        adWarning.setWarningStatus(Constant.WARING_WAIT_DEAL);
        adWarning.setContent(content);
        adWarning.setStatus(Constant.STATUS_NORMAL);
        adWarning.setCompanyId(tUser.getCompanyId());
        adWarning.insert();
        if (EmptyUtils.isNotEmpty(pics)){
            JSONArray jsonArray = JSONArray.fromObject(pics);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject x = jsonArray.getJSONObject(i);
                PicFile picFile=new PicFile();
                picFile.setPicId(UUID.randomUUID().toString());
                picFile.setPath(x.getString("path"));
                picFile.setType(Constant.PIC_WARING_PIC);
                picFile.setBusnessId(warningId);
                picFile.setStatus(Constant.STATUS_NORMAL);
                picFile.insert();
            }
        }
         return ResultInfoUtils.infoData();
    }

    @Override
    public String handleWarning(String content, String userId, String warningId) {
        //是否存在未审核的处理
        Integer num = warningHandleDao.selectCount(new EntityWrapper<WarningHandle>()
                .eq("warning_id", warningId)
                .eq("status", Constant.STATUS_NORMAL)
                .eq("handle_status", Constant.HANDLE_STATUS_WAIT));
        if (num>0){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"还有处理为审核，请审核后再次添加");
        }

        AdWarning adWarning=new AdWarning();
        adWarning.setWarningId(warningId);
        adWarning=adWarning.selectById();
        if (EmptyUtils.isEmpty(adWarning)){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"报警不存在");
        }
        if (Constant.WARING_FINISH==adWarning.getWarningStatus()){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"报警已完成");
        }
        WarningHandle warningHandle = new WarningHandle();
        warningHandle.setHandleId(UUID.randomUUID().toString());
        warningHandle.setContent(content);
        warningHandle.setHandleStatus(Constant.HANDLE_STATUS_WAIT);
        warningHandle.setStatus(Constant.STATUS_NORMAL);
        warningHandle.setWarningId(warningId);
        warningHandle.insert();
        setWarningUnRead(warningId);
        return ResultInfoUtils.infoData();
    }

    private void setWarningUnRead(String warningId) {
        if (EmptyUtils.isEmpty(warningId)){
            return;
        }
        MsgRead msgRead=new MsgRead();
        boolean warning_id = msgRead.delete(new EntityWrapper()
                .eq("warning_id", warningId));
    }

    @Override
    public String auditWarningHandle(String userId, String warningId, Integer auditStatus) {
        //用户是否为监管者
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (2!=tUser.getUserType()){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"该用户不为监管者");
        }
        //是否存在可以审核的报警处理
        WarningHandle t = new WarningHandle();
        t.setWarningId(warningId);
        t.setHandleStatus(Constant.HANDLE_STATUS_WAIT);
        t.setStatus(Constant.STATUS_NORMAL);
        WarningHandle warningHandle = warningHandleDao.selectOne(t);
        if (EmptyUtils.isEmpty(warningHandle)){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"不存在待审核的处理");
        }
        if (auditStatus==1){
            warningHandle.setHandleStatus(Constant.WARING_FINISH);
            warningHandle.updateById();
            AdWarning adWarning=new AdWarning();
            adWarning.setWarningId(warningId);
            adWarning.setWarningStatus(Constant.HANDLE_STATUS_PASS);
            adWarning.updateById();
        }else {
            warningHandle.setHandleStatus(Constant.HANDLE_STATUS_NOT_PASS);
            warningHandle.updateById();
        }
        setWarningUnRead(warningId);
        return ResultInfoUtils.infoData();
    }

    @Override
    public void readWarning(String warningId, String userId) {
        MsgRead msgRead=new MsgRead();
        msgRead=msgRead.selectOne(new EntityWrapper()
        .eq("warning_id",warningId)
        .eq("user_id",userId));
        if (EmptyUtils.isEmpty(msgRead)){
            msgRead=new MsgRead();
            msgRead.setMsgReadId(UUID.randomUUID().toString());
            msgRead.setWarningId(warningId);
            msgRead.setUserId(userId);
            msgRead.insert();
        }
    }
}
