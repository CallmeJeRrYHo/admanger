package com.hjh.service.impl;

import com.hjh.constant.Constant;
import com.hjh.entity.Msg;
import com.hjh.dao.MsgDao;
import com.hjh.entity.PicFile;
import com.hjh.entity.TUser;
import com.hjh.service.IMsgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.EmptyUtils;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class MsgServiceImpl extends ServiceImpl<MsgDao, Msg> implements IMsgService {
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String addMsg(String userId, String content, String pics) {
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (2!=tUser.getUserType()){
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"用户不为监管者");
        }
        Msg msg=new Msg();
        msg.setContent(content);
        msg.setStatus(Constant.STATUS_NORMAL);
        msg.setCreateUserId(userId);
        String msgId = UUID.randomUUID().toString();
        msg.setMsgId(msgId);
        msg.insert();
        if (EmptyUtils.isNotEmpty(pics)) {
            JSONArray jsonArray = JSONArray.fromObject(pics);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PicFile picFile = new PicFile();
                picFile.setPicId(jsonObject.getString("pic_id"));
                picFile.setPath(jsonObject.getString("path"));
                picFile.setBusnessId(msgId);
                picFile.setStatus(Constant.STATUS_NORMAL);
                picFile.setType(Constant.PIC_MSG_PIC);
                picFile.insert();
            }
        }
        return ResultInfoUtils.infoData();
    }
}
