package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjh.dao.AdvertisementDao;
import com.hjh.entity.PicFile;
import com.hjh.service.IAdWarningService;
import com.hjh.service.IAdvertisementService;
import com.yqh.util.common.BaseController;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 21:24 2018/9/12
 */
@Controller
@RequestMapping("/advertisement")
public class AdvertisementController extends BaseController {
    @Autowired
    AdvertisementDao advertisementDao;
    @Autowired
    IAdWarningService adWarningService;
    @Autowired
    IAdvertisementService iAdvertisementService;
    @ResponseBody
    @RequestMapping("/selectAdList")
    public String selectAdList(String companyId, Integer adType, Integer adSpec, String keyWord, @RequestParam(defaultValue = "1")Integer index,@RequestParam(defaultValue = "10")Integer pageSize){
        try{
            // TODO: 2018/9/12 dao层添加index
            List<Map<String, Object>> maps = advertisementDao.selectAdList(companyId, adType, adSpec, keyWord, index, pageSize);
            return ResultInfoUtils.infoData(maps);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/getAdDetail")
    public String getAdDetail(String advertisementId){
        try{
            checkNecessaryParameter("advertisementId",advertisementId);
            List<Map<String, Object>> adDetail = advertisementDao.getAdDetail(advertisementId);
            PicFile picFile=new PicFile();
            List<PicFile> picFiles = picFile.selectList(new EntityWrapper().eq("busness_id", advertisementId)
                    .eq("status", 1)
                    .eq("type", 3));
            if (adDetail.size()<1){
                throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"广告id有误");
            }
            adDetail.get(0).put("pics",picFiles);
            return ResultInfoUtils.infoData(adDetail.get(0));

        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/sendWarning")
    public String sendWarning(String advertisementId,String userId,String content,String  pics){
        try{
            checkNecessaryParameter("advertisementId",advertisementId);
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("内容",content);
            return adWarningService.sendWarning(advertisementId,userId,content,pics);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/selectMyAd")
    public String selectMyAd(String userId,Integer adType,Integer adSpec,@RequestParam(defaultValue = "1") Integer index,@RequestParam(defaultValue = "10") Integer pageSize){
        try{
        checkNecessaryParameter("userId",userId);

            return iAdvertisementService.selectMyAd(userId,adType,adSpec,index,pageSize);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/deleteAd")
    public String deleteAd(String userId,String advertisementId){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("advertisementId",advertisementId);
            return iAdvertisementService.deleteAd(userId,advertisementId);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/addAdvertisement")
    public String addAdvertisement(String userId,String serial_num,Double lontitude,Double latitude,Integer adType,Integer adSpec,Integer hasLeaderPortrait,String adContent,String pic){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("序号",serial_num);
            checkNecessaryParameter("经度",lontitude);
            checkNecessaryParameter("纬度",latitude);
            checkNecessaryParameter("广告类型",adType);
            checkNecessaryParameter("广告序号",adSpec);
            checkNecessaryParameter("序号",hasLeaderPortrait);
            checkNecessaryParameter("广告内容",adContent);
            checkNecessaryParameter("图片",pic);

            return iAdvertisementService.addAdvertisement(userId,serial_num,lontitude,latitude,adType,adSpec,hasLeaderPortrait,adContent,pic);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/designAudit")
    public String designAudit(String userId,String advertisementId,String auditResponse,Integer auditStatus){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("advertisementId",advertisementId);
            checkNecessaryParameter("审核状态",auditStatus);
            if (2==auditStatus){
                checkNecessaryParameter("审核回复",auditResponse);
            }
            return iAdvertisementService.designAudit(userId,advertisementId,auditResponse,auditStatus);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/submitLiveViewAudit")
    public String submitLiveViewAudit(String userId,String advertisementId,String pic){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("advertisementId",advertisementId);
            checkNecessaryParameter("图片",pic);

            return iAdvertisementService.submitLiveViewAudit(userId,advertisementId,pic);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/auditLiveView")
    public String auditLiveView(String userId,String advertisementId){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("advertisementId",advertisementId);
            return iAdvertisementService.auditLiveView(userId,advertisementId);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
