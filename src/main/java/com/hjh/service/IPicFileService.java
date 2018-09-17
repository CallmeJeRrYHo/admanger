package com.hjh.service;

import com.hjh.entity.PicFile;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface IPicFileService extends IService<PicFile> {

    String uploadFile(HttpServletRequest request, MultipartFile file) throws Exception;
}
