package com.hjh.service.impl;

import com.hjh.constant.Constant;
import com.hjh.entity.PatrolRecord;
import com.hjh.dao.PatrolRecordDao;
import com.hjh.entity.PicFile;
import com.hjh.service.IPatrolRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-12-06
 */
@Service
public class PatrolRecordServiceImpl extends ServiceImpl<PatrolRecordDao, PatrolRecord> implements IPatrolRecordService {

    @Override
    @Transactional
    public String add(String userId, Integer hasProblem, Integer isWarning, String warningNo, String pics, String advertisementId) {
        String id = UUID.randomUUID().toString();
        PatrolRecord patrolRecord=new PatrolRecord();
        patrolRecord.setPatrolRecordId(id);
        patrolRecord.setUserId(userId);
        patrolRecord.setHasProblem(hasProblem);
        patrolRecord.setAdvertisementId(advertisementId);
        patrolRecord.setStatus(Constant.STATUS_NORMAL);
        patrolRecord.setIsWarning(isWarning);
        patrolRecord.setWarningNo(warningNo);
        patrolRecord.insert();
        if (EmptyUtils.isNotEmpty(pics)){
            JSONArray jsonArray = JSONArray.fromObject(pics);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject x = jsonArray.getJSONObject(i);
                PicFile picFile=new PicFile();
                picFile.setPicId(UUID.randomUUID().toString());
                picFile.setPath(x.getString("path"));
                picFile.setType(Constant.PIC_PATROL_RECORD_PIC);
                picFile.setBusnessId(id);
                picFile.setStatus(Constant.STATUS_NORMAL);
                picFile.insert();
            }
        }
        return ResultInfoUtils.infoData();
    }
}
