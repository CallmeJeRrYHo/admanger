package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

public class JsonUtil {
    public static final String JSON_CONTENT_TYPE = "text/plain; charset=UTF-8";

    public JsonUtil() {
    }


    public static Map getMap4Json(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        HashMap valueMap = new HashMap();

        while(keyIter.hasNext()) {
            String key = (String)keyIter.next();
            Object value = jsonObject.get(key);
            valueMap.put(key, value);
        }

        return valueMap;
    }

    public static Object[] getObjectArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

    public static List getList4Json(String jsonString, Class pojoClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List list = new ArrayList();

        for(int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object pojoValue = JSONObject.toBean(jsonObject, pojoClass);
            list.add(pojoValue);
        }

        return list;
    }

    public static String[] getStringArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); ++i) {
            stringArray[i] = jsonArray.getString(i);
        }

        return stringArray;
    }

    public static Long[] getLongArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Long[] longArray = new Long[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); ++i) {
            longArray[i] = jsonArray.getLong(i);
        }

        return longArray;
    }

    public static Integer[] getIntegerArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Integer[] integerArray = new Integer[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); ++i) {
            integerArray[i] = jsonArray.getInt(i);
        }

        return integerArray;
    }

    public static Date[] getDateArray4Json(String jsonString, String DataFormat) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Date[] dateArray = new Date[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); ++i) {
            String dateString = jsonArray.getString(i);
            Date date = TimeUtils.string2Date(dateString, new SimpleDateFormat(DataFormat, Locale.getDefault()));
            dateArray[i] = date;
        }

        return dateArray;
    }

    public static Double[] getDoubleArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Double[] doubleArray = new Double[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); ++i) {
            doubleArray[i] = jsonArray.getDouble(i);
        }

        return doubleArray;
    }

    public static String getJsonString4JavaPOJO(Object javaObj) {
        JSONObject json = JSONObject.fromObject(javaObj);
        return json.toString();
    }

    public static String stringFormatToJson(String s) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch(c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat) {
        JsonConfig jsonConfig = configJson(dataFormat);
        JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
        return json.toString();
    }

    public static void main(String[] args) {
    }

    public static JsonConfig configJson(String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{""});
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor(datePattern));
        return jsonConfig;
    }

    public static JsonConfig configJson(String[] excludes, String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor(datePattern));
        System.out.println(22);
        return jsonConfig;
    }

    public static JSONObject writeJsonWithMsg(String status, String msg) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("status", status);
        jsonObject.element("msg", msg);
        return jsonObject;
    }

    public static JSONObject writeJsonObjWithMsg(String status, Object obj) throws IOException {
        return writeJsonObjWithMsg(status, obj, (String[])null, (String)null);
    }

    public static JSONObject writeJsonObjWithMsg(String status, Object obj, String[] excludes, String dateFormate) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("status", status);
        jsonObject.element("data", JSONObject.fromObject(obj, configJson(excludes, "yyyy-MM-dd HH:mm:ss")));
        return jsonObject;
    }

    public static JSONObject writeJsonObj(Object object, String[] excludes, String dateFormate) throws IOException, Exception {
        JSONObject jsonObject = JSONObject.fromObject(object, configJson(excludes, dateFormate));
        return jsonObject;
    }

    public static JSONObject writeJsonObject(Object object, String[] excludes) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(object, configJson(excludes, "yyyy-MM-dd"));
        return jsonObject;
    }

    public static JSONObject writeJsonObjectWithConfig(Object object, JsonConfig config) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(object, config);
        return jsonObject;
    }

    public static JSONObject writeBaseJsonWithMsg(String status, Object obj) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("status", status);
        jsonObject.element("data", obj);
        return jsonObject;
    }

    public static JSONObject writeJsonListWithMsg(String status, List<?> dataList) throws IOException {
        JSONArray jsonObjects = JSONArray.fromObject(dataList, configJson((String[])null, "yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("status", status);
        jsonObject.element("dataList", jsonObjects);
        return jsonObject;
    }

    public static JSONObject writeJsonListWithMsg(String status, List<?> dataList, String[] excludes, String dateFormate) throws IOException {
        JSONArray jsonObjects = JSONArray.fromObject(dataList, configJson(excludes, "yyyy-MM-dd HH:mm:ss"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("status", status);
        jsonObject.element("dataList", jsonObjects);
        return jsonObject;
    }

    public static boolean isJson(String jsonData) {
        try {
            if (jsonData.indexOf("[") == 0) {
                JSONArray array = JSONArray.fromObject(jsonData);
                if (null == array) {
                    return false;
                }
            } else {
                if (jsonData.indexOf("{") != 0) {
                    return false;
                }

                JSONObject jsonObject = JSONObject.fromObject(jsonData);
                if (null == jsonObject) {
                    return false;
                }
            }

            return true;
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static String filterNull(JSONObject jsonObj) {
        Iterator<String> it = jsonObj.keys();
        Object obj = null;
        String key = null;

        while(true) {
            do {
                if (!it.hasNext()) {
                    return jsonObj.toString();
                }

                key = (String)it.next();
                obj = jsonObj.get(key);
                if (obj instanceof JSONObject) {
                    filterNull((JSONObject)obj);
                }

                if (obj instanceof JSONArray) {
                    JSONArray objArr = (JSONArray)obj;

                    for(int i = 0; i < objArr.size(); ++i) {
                        filterNull(objArr.getJSONObject(i));
                    }
                }
            } while(obj != null && !(obj instanceof JSONNull));

            jsonObj.put(key, "");
        }
    }
}
