package com.hjh.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个推util
 * @author wyh
 */
@Component
public class GexinUtil {

    @Value("${gt.appid}")
    private  String appId ;
    @Value("${gt.appkey}")
    private  String appKey;
    @Value("${gt.appsecret}")
    private  String appSecret ;
    @Value("${gt.appmastersecret}")
    private  String masterSecret;
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";  //
    private static boolean useSSL = true;   //是否使用http接口调用：true为https，false为http。默认为http

    /*public static void main(String[] args) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("來了");
        template.setText("還行");
        template.setUrl("http://getui.com");
        
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
	}*/
    
    /**
     * 单个用户推送
     *
     * @param CID
     * @return
     */
    public  boolean pushMessageToSingle(AbstractTemplate notificationTemplate, String CID) {
        System.out.println(appKey+" "+masterSecret+" ");
        IGtPush push = new IGtPush(appKey, masterSecret, true);
//        LinkTemplate template = linkTemplateDemo();
        SingleMessage message = new SingleMessage();
//        message.setOffline(true);
//        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(notificationTemplate);
//        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
            return true;
        } else {
            return false;
        }
    }


    /**
     * 批量用户发送
     */
    public  boolean pushMessageToSingle(AbstractTemplate notificationTemplate, List<String> CIDs) {
        try {
            for(String cid : CIDs){
                pushMessageToSingle(notificationTemplate, cid);
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 向app所有用户发送
     *
     * @param notificationTemplate
     * @return
     */
    public boolean pushMessageToApp(AbstractTemplate notificationTemplate) {

        // 此处true为https域名，false为http，默认为false。java推荐使用此方法，没有特别需求不需要指定host。
        IGtPush push = new IGtPush(appKey, masterSecret, useSSL);


        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);


        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(notificationTemplate);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setPushNetWorkType(0);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret =null;
        try {
            ret =  push.pushMessageToApp(message);
        } catch (RequestException e) {
            e.printStackTrace();
            ret =  push.pushMessageToApp(message, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通知内容+样式
     * @param title
     * @param text
     * @return
     */
    public NotificationTemplate notificationTemplateDemo(String title , String text, String content) {
        System.out.println("in+++++++++++++++++++++++++++++++++");
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        return template;
    }


    public TransmissionTemplate transmissionTemplateDemo(String keyWord) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(keyWord);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }


    /**
     * 测试
     * @param args
//     */
//    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("type",2);
////        List<Map<String,Object>> companyList = xsUserDao.select(map,null,null);
//        List<String> clients = new ArrayList<>();
////        for(Map<String,Object> single : companyList){
////            clients.add((String) single.get("client_id"));
////        }
////                boolean result =false;
//
//        clients.add("b2c851f7acd20d2e4a1737a8a638a08c");
//
//        try {
//            JSONObject jsonObject=new JSONObject();
//
//            jsonObject.put("operation","SCAN_CODE_PAY");
//            jsonObject.put("message", "淘商品到账1111元");
//            TransmissionTemplate transmissionTemplate = GexinUtil.transmissionTemplateDemo(jsonObject.toString());
//            boolean  result = GexinUtil.pushMessageToSingle(transmissionTemplate,"afe8a0cc5c5809f72c3c6369d95c755e");
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("推送失败");
//        }
//    }

}
