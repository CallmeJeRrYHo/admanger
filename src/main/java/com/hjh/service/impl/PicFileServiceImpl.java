package com.hjh.service.impl;

import com.hjh.entity.PicFile;
import com.hjh.dao.PicFileDao;
import com.hjh.service.IPicFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.hjh.utils.FilesUploadUtils;
import com.hjh.utils.ResultInfoUtils;

import com.hjh.utils.TimeUtils;
import com.hjh.utils.WaterMarkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public class PicFileServiceImpl extends ServiceImpl<PicFileDao, PicFile> implements IPicFileService {
    @Value("${pic.UPLOAD_FILE_PATH}")
    String filePath;
    @Override
    public String uploadFile(HttpServletRequest request, MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        System.err.println(contentType);
        String fileName = file.getOriginalFilename();
        //返回json
        fileName = FilesUploadUtils.uploadFile(file.getBytes(), filePath, fileName);
        Date date = new Date();
        String date2String = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"));
        Color color=new Color(255,255,255,200);                               //水印图片色彩以及透明度
        Font font = new Font("微软雅黑", Font.PLAIN, 35);                     //水印字体
        String[] split = fileName.split(",");
        String newFileWithWM = split[0] + "-wm.jpg";
        WaterMarkUtils.addWaterMark(filePath+fileName, filePath + newFileWithWM,date2String,color,font);
        String url = "/pic/" + newFileWithWM;
        Map<String, Object> map = new HashMap<>();
        String id = UUID.randomUUID().toString();
        map.put("path", url);
        map.put("pic_id", id);
        return ResultInfoUtils.infoData(map);
    }
}
