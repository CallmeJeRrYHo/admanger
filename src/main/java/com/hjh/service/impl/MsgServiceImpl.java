package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hjh.constant.Constant;
import com.hjh.dao.TUserDao;
import com.hjh.entity.*;
import com.hjh.dao.MsgDao;
import com.hjh.service.IMsgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class MsgServiceImpl extends ServiceImpl<MsgDao, Msg> implements IMsgService {

    @Autowired
    TUserDao tUserDao;

    @Autowired
    GexinUtil gexinUtil;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public String addMsg(String userId, String content, String pics, String companyIds) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (2 != tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "用户不为监管者");
        }
        Msg msg = new Msg();
        msg.setContent(content);
        msg.setStatus(Constant.STATUS_NORMAL);
        msg.setCreateUserId(userId);
        String msgId = UUID.randomUUID().toString();
        msg.setMsgId(msgId);
        msg.insert();
        String[] split = companyIds.split(",");
        for (String s : split) {
            MsgCompany msgCompany = new MsgCompany();
            msgCompany.setMsgCompanyId(UUID.randomUUID().toString());
            msgCompany.setCompanyId(s);
            msgCompany.setMsgId(msgId);
            msgCompany.setStatus(Constant.STATUS_NORMAL);
            msgCompany.insert();
        }
        List<String> strings = Arrays.asList(split);
        List<String> cids = tUserDao.selectPushCidByCompanyIds(strings);
        JSONObject object = new JSONObject();
        object.put("operation","msg");
        TransmissionTemplate transmissionTemplate = gexinUtil.transmissionTemplateDemo(object.toString());
        gexinUtil.pushMessageToSingle(transmissionTemplate,cids);
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

    @Override
    public String readMsg(String msgId, String userId) {
        MsgRead msgRead = new MsgRead();
        int i = msgRead.selectCount(new EntityWrapper().eq("msg_id", msgId)
                .eq("user_id", userId));
        if (i == 0) {
            msgRead.setMsgReadId(UUID.randomUUID().toString());
            msgRead.setUserId(userId);
            msgRead.setMsgId(msgId);
            msgRead.insert();
        }
        return ResultInfoUtils.infoData();
    }
}
