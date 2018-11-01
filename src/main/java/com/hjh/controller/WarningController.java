package com.hjh.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.constant.Constant;
import com.hjh.dao.AdWarningDao;
import com.hjh.dao.PicFileDao;
import com.hjh.dao.WarningHandleDao;
import com.hjh.entity.AdWarningWithPic;
import com.hjh.entity.PicFile;
import com.hjh.entity.WarningHandle;
import com.hjh.service.IAdWarningService;
import com.hjh.utils.BaseController;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
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
 * @Date： Create in 15:35 2018/9/14
 */
@Controller
@RequestMapping("/warning")
public class WarningController extends BaseController {
    @Autowired
    AdWarningDao adWarningDao;
    @Autowired
    WarningHandleDao warningHandleDao;
    @Autowired
    PicFileDao picFileDao;
    @Autowired
    IAdWarningService iAdWarningService;
    @ResponseBody
    @RequestMapping("/selectMyWarning")
    public String  selectMyWarning(String userId, @RequestParam(defaultValue = "1") Integer index, @RequestParam(defaultValue = "10")Integer pageSize){
        try{
            checkNecessaryParameter("userId",userId);
            List<AdWarningWithPic> adWarnings = adWarningDao.selectMyWarning(new Page<AdWarningWithPic>(index, pageSize), userId);
            long l = adWarningDao.selectMyWarningCount(userId);
            return ResultInfoUtils.infoData(adWarnings,l);
        }catch(Exception e){
            return handleError(e);
        }
    }

    @ResponseBody
    @RequestMapping("/getWarningDetail")
    public String getWarningDetail(String userId,String warningId){
        try{
            checkNecessaryParameter("userId",userId);

            checkNecessaryParameter("warningId",warningId);
            AdWarningWithPic warningDetail = adWarningDao.getWarningDetail(warningId);
            if (EmptyUtils.isEmpty(warningDetail)){
                throw new YqhException(BaseMessageEnum.UNKNOW_ERROR,"报警不存在");
            }
            List<WarningHandle> warningHandles = warningHandleDao.selectList(new EntityWrapper<WarningHandle>()
                    .eq("warning_id", warningId)
                    .eq("status", Constant.STATUS_NORMAL)
                    .orderBy("create_time", true));
            for (WarningHandle warningHandle : warningHandles) {
                List<PicFile> maps = picFileDao.selectList(new EntityWrapper<PicFile>()
                        .eq("busness_id", warningHandle.getHandleId())
                        .eq("status", Constant.STATUS_NORMAL)
                        .eq("type", Constant.PIC_WARING_HANDLE_PIC));
                warningHandle.setPics(maps);
            }
            Integer waitAuditNum = warningHandleDao.selectCount(new EntityWrapper<WarningHandle>()
                    .eq("warning_id", warningId)
                    .eq("status", Constant.STATUS_NORMAL)
                    .eq("handle_status", Constant.HANDLE_STATUS_WAIT));
            if (Constant.WARING_FINISH==warningDetail.getWarningStatus()){
                warningDetail.setIsShowHandle(0);
                warningDetail.setIsShowHandleAudit(0);
            }else {
                //有待处理的或者已完成的不显示处理框
                boolean hasWaitAudit = waitAuditNum > 0;
                warningDetail.setIsShowHandle(hasWaitAudit ? 0 : 1);
                warningDetail.setIsShowHandleAudit(hasWaitAudit? 1 : 0);
            }
            warningDetail.setWarningHandles(warningHandles);
            iAdWarningService.readWarning(warningId,userId);
            return ResultInfoUtils.infoData(warningDetail);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @ResponseBody
    @RequestMapping("/handleWarning")
    public String handleWarning(String content,String userId,String warningId,String  pics){
        try{
            checkNecessaryParameter("内容",content);
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("warningId",warningId);

            return iAdWarningService.handleWarning(content,userId,warningId,pics);
        }catch(Exception e){
            return handleError(e);
        }
    }
    @ResponseBody
    @RequestMapping("/auditWarningHandle")
    public String auditWarningHandle(String userId,String warningId,Integer auditStatus){
        try{
            checkNecessaryParameter("userId",userId);
            checkNecessaryParameter("warningId",warningId);
            checkNecessaryParameter("auditStatus",auditStatus);
            return iAdWarningService.auditWarningHandle(userId,warningId,auditStatus);
        }catch(Exception e){
            return handleError(e);
        }
    }
}
