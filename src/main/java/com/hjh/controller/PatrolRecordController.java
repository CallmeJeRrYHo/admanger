package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.constant.Constant;
import com.hjh.dao.PatrolRecordDao;
import com.hjh.dao.PicFileDao;
import com.hjh.entity.PatrolRecord;
import com.hjh.service.IPatrolRecordService;
import com.hjh.utils.BaseController;
import com.hjh.utils.ResultInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 10:01 2018/12/6
 */
@RequestMapping("/patrolRecord")
@RestController
public class PatrolRecordController extends BaseController{
    @Autowired
    IPatrolRecordService iPatrolRecordService;
    @Autowired
    PatrolRecordDao patrolRecordDao;
    @Autowired
    PicFileDao picFileDao;
    @RequestMapping("/add")
    public String add(String userId,Integer hasProblem,Integer isWarning,String warningNo,String pics,String advertisementId){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("advertisementId",advertisementId);
            checkNecessaryParameter("是否有问题",hasProblem);
            checkNecessaryParameter("是否报警",isWarning);
            return iPatrolRecordService.add(userId,hasProblem,isWarning,warningNo,pics,advertisementId);
        }catch(Exception e){
            return handleError(e);
        }
    }


    @RequestMapping("/list")
    public String list(String advertisementId){
        try{
            checkNecessaryParameter("advertisementId",advertisementId);
            List<Map<String, Object>> patrolRecords = patrolRecordDao.selectMaps(new EntityWrapper<PatrolRecord>().eq("advertisement_id", advertisementId).eq("status", Constant.STATUS_NORMAL));
            return ResultInfoUtils.infoData(patrolRecords);
        }catch(Exception e){
            return handleError(e);
        }
    }


    @RequestMapping("/detail")
    public String detail(String patrolRecordId){
        try{
            checkNecessaryParameter("patrolRecordId",patrolRecordId);
            PatrolRecord p=new PatrolRecord();
            p.setStatus(Constant.STATUS_NORMAL);
            p.setPatrolRecordId(patrolRecordId);
            List<Map<String, Object>> patrolRecord = patrolRecordDao.selectMaps(new EntityWrapper<PatrolRecord>().eq("status",Constant.STATUS_NORMAL)
            .eq("patrol_record_id",patrolRecordId));
            if (patrolRecord.size()<=0){
                throw new RuntimeException("不存在");
            }
            List<Map<String, Object>> maps = picFileDao.selectPatrolRecordPic(patrolRecordId);
            patrolRecord.get(0).put("pics",maps);
            return ResultInfoUtils.infoData(patrolRecord.get(0));
        }catch(Exception e){
            return handleError(e);
        }

    }
}
