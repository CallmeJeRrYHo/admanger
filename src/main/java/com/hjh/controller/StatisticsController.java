package com.hjh.controller;

import com.hjh.dao.AdvertisementDao;
import com.hjh.service.IAdvertisementService;
import com.hjh.utils.BaseController;
import com.hjh.utils.ResultInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 21:23 2018/10/3
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {
    @Autowired
    IAdvertisementService iAdvertisementService;
    @Autowired
    AdvertisementDao advertisementDao;
    @RequestMapping("/adStatistics")
    public String adStatistics(String companyId,Integer type){
        try{
            checkNecessaryParameter("companyId",companyId);
            checkNecessaryParameter("type",type);

            return iAdvertisementService.adStatistics(companyId,type);

        }catch(Exception e){
            return handleError(e);
        }
    }


    @RequestMapping("/newStatistics")
    public String newStatistics(String companyId, String startDate, String endDate){
        try{
            return ResultInfoUtils.infoData(advertisementDao.newAdStatistics(companyId));
        }catch(Exception e){
            return handleError(e);
        }
    }
}
