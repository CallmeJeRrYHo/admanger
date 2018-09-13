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
}
