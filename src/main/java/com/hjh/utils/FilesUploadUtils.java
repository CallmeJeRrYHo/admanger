package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FilesUploadUtils {
    private static int TO_FORMAT = 0;
    private static int TO_MAX = 1;
    private static int TO_OK = 200;

    public FilesUploadUtils() {
    }



    public static Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, String url) {
        String savePath = url == null ? request.getSession().getServletContext().getRealPath("/") + "/ocr" : url;
        File saveFile = new File(savePath);
        if (!saveFile.isDirectory()) {
            saveFile.mkdirs();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        int biglength = 20;
        upload.setSizeMax((long)(1048576 * biglength));
        HashMap result = new HashMap();

        try {
            request.setCharacterEncoding("UTF-8");
            String[] image = new String[]{".jpg", ".jpeg", ".gif", ".png", ".bmp"};
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator var11 = fileItems.iterator();

            while(var11.hasNext()) {
                FileItem item = (FileItem)var11.next();
                String name = item.getFieldName();
                String fileName;
                if (item.isFormField()) {
                    fileName = item.getString("UTF-8");
                    result.put(name, fileName);
                } else {
                    fileName = item.getName();
                    if (null != fileName && !"".equals(fileName)) {
                        String suffix = fileName.substring(fileName.lastIndexOf("."));
                        String uuID = UUID.randomUUID().toString().trim().replaceAll("-", "");
                        if (Arrays.asList(image).contains(suffix.toLowerCase())) {
                            String imageName = uuID.toString() + suffix;
                            item.write(new File(savePath, imageName));
                            result.put("path", savePath + "/" + imageName);
                            result.put("relative", (url == null ? "ocr/" : "") + imageName);
                            result.put("name", fileName);
                            result.put("suffix", suffix);
                            result.put("status", TO_OK);
                        } else {
                            result.put("status", TO_FORMAT);
                            result.put("message", "上传文件格式错误");
                        }
                    }
                }
            }

            return result;
        } catch (FileSizeLimitExceededException var18) {
            var18.printStackTrace();
            result.put("status", TO_MAX);
            result.put("message", "上传文件超出最大值20M");
            return result;
        } catch (SizeLimitExceededException var19) {
            var19.printStackTrace();
        } catch (Exception var20) {
            var20.printStackTrace();
        }

        return result;
    }

    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String uuID = UUID.randomUUID().toString().trim().replaceAll("-", "");
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        String fileAllName = filePath + uuID + suffix;
        FileOutputStream out = new FileOutputStream(fileAllName);
        out.write(file);
        out.flush();
        out.close();
        return uuID + suffix;
    }
}
