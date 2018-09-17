package com.hjh.service.impl;

import com.hjh.entity.PicFile;
import com.hjh.dao.PicFileDao;
import com.hjh.service.IPicFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yqh.util.common.FilesUploadUtils;
import com.yqh.util.common.PropUtils;
import com.yqh.util.common.ResultInfoUtils;
import com.yqh.util.common.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        String url = "/pic/" + FilesUploadUtils.uploadFile(file.getBytes(), filePath, fileName);
        Map<String, Object> map = new HashMap<>();
        String id = UUIDUtil.generateUUID();
        map.put("path", url);
        map.put("pic_id", id);
        return ResultInfoUtils.infoData(map);
    }
}
