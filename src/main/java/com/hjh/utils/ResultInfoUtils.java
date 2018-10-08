package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.hjh.utils.BaseMessageEnum;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResultInfoUtils {
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    private static Logger logger = LogManager.getLogger(ResultInfoUtils.class.getName());
    private static StackTraceElement[] parentClasses;
    private static String retCodeKey = "code";
    private static String retMessKey = "message";
    private static String errorNumKey = "errorNum";
    private static String retTotal = "total";
    private static String dataKey = "result";
    private static String dataKey2 = "result2";
    private static String totalKey = "total";
    private static String retStatus = "status";
    private static String retRemark = "remark";

    public ResultInfoUtils() {
    }

    public static String infoData(int retCode, String message) {
        return infoData(retCode, message, (Object)null, (Object)null, (Long)null, (String)null, (String)null);
    }

    public static String infoData(int retCode, String message, Object dataObj) {
        return infoData(retCode, message, dataObj, (Object)null, (Long)null, (String)null, (String)null);
    }

    public static String infoData(int retCode, String message, Object dataObj, long total) {
        return infoData(retCode, message, dataObj, (Object)null, total, (String)null, (String)null);
    }

    public static String infoData(int retCode, String message, Object dataObj, String status) {
        return infoData(retCode, message, dataObj, (Object)null, (Long)null, status, (String)null);
    }

    public static String infoData(int retCode, String message, Object dataObj, Object dataObj2, Long total, String status, String remark) {
        Map<String, Object> resultMap = new HashMap();
        resultMap.put(retCodeKey, retCode);
        resultMap.put(retMessKey, message);
        if (EmptyUtils.isNotEmpty(status)) {
            resultMap.put(retStatus, status);
        }

        resultMap.put(dataKey, dataObj);
        if (EmptyUtils.isNotEmpty(dataObj2)) {
            resultMap.put(dataKey2, dataObj2);
        }

        resultMap.put(retTotal, total);
        if (EmptyUtils.isNotEmpty(remark)) {
            resultMap.put(retRemark, remark);
        }

        JSONObject jsonObject = JSONObject.fromObject(resultMap, JsonUtil.configJson("yyyy-MM-dd HH:mm:ss"));
        String result = JsonUtil.filterNull(jsonObject);
        resultMap = null;
        if (System.getProperty("isDebug") != null) {
            parentClasses = Thread.currentThread().getStackTrace();
            logger.debug("[类" + parentClasses[2].getClassName() + "方法" + parentClasses[2].getMethodName() + "第" + parentClasses[2].getLineNumber() + "行] - " + result);
        }

        return result;
    }

    public static String errorData(int retCode, String message) {
        String errorNum = String.valueOf(System.currentTimeMillis()) + (new Random()).nextInt(1000);
        return errorData(errorNum, retCode, message);
    }

    public static String errorData(int retCode, String message, String errorMsg) {
        String errorNum = String.valueOf(System.currentTimeMillis()) + (new Random()).nextInt(1000) + errorMsg;
        return errorData(errorNum, retCode, message);
    }

    public static String errorData(int retCode, String message, Throwable cause) {
        String errorNum = String.valueOf(System.currentTimeMillis()) + (new Random()).nextInt(1000);
        Map<String, Object> resultMap = new HashMap();
        resultMap.put(retCodeKey, retCode);
        resultMap.put(retMessKey, message);
        resultMap.put(errorNumKey, errorNum);
        String result = JsonUtil.filterNull(JSONObject.fromObject(resultMap));
        parentClasses = Thread.currentThread().getStackTrace();
        StackTraceElement[] var6 = parentClasses;
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            StackTraceElement stackTraceElement = var6[var8];
            if (stackTraceElement.getClassName().contains("com.kingold")) {
                logger.info("[错误编号: " + errorNum + "][类" + stackTraceElement.getClassName() + "方法" + stackTraceElement.getMethodName() + "第" + stackTraceElement.getLineNumber() + "行] - " + result);
            }
        }

        return result;
    }

    public static String errorData(String errorNum, int retCode, String message) {
        parentClasses = Thread.currentThread().getStackTrace();
        Map<String, Object> resultMap = new HashMap();
        resultMap.put(retCodeKey, retCode);
        resultMap.put(retMessKey, message);
        resultMap.put(errorNumKey, errorNum);
        String result = JsonUtil.filterNull(JSONObject.fromObject(resultMap));
        logger.info("[错误编号: " + errorNum + "][类" + parentClasses[2].getClassName() + "方法" + parentClasses[2].getMethodName() + "第" + parentClasses[2].getLineNumber() + "行] - " + result);
        resultMap = null;
        return result;
    }

    public static String infoData(Object dataObj, int total) {
        return infoData(BaseMessageEnum.SUCCESS.getCode(), BaseMessageEnum.SUCCESS.getMessage(), dataObj, (Object)null, (long)total, (String)null, (String)null);
    }

    public static String infoData(Object dataObj, long total) {
        return infoData(BaseMessageEnum.SUCCESS.getCode(), BaseMessageEnum.SUCCESS.getMessage(), dataObj, (Object)null, total, (String)null, (String)null);
    }

    public static String infoData(Object dataObj) {
        return infoData(BaseMessageEnum.SUCCESS.getCode(), BaseMessageEnum.SUCCESS.getMessage(), dataObj, (Object)null, (Long)null, (String)null, (String)null);
    }

    public static String infoData() {
        return infoData(BaseMessageEnum.SUCCESS.getCode(), BaseMessageEnum.SUCCESS.getMessage(), (Object)null, (Object)null, (Long)null, (String)null, (String)null);
    }
}
