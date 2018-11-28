package com.hjh.service.impl;

import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hjh.service.IMsgService;
import com.hjh.utils.GexinUtil;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 11:54 2018/11/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgServiceImplTest {

    @Autowired
    GexinUtil gexinUtil;

    @Autowired
    IMsgService iMsgService;
    @Test
    public void push(){
        NotificationTemplate notificationTemplate = gexinUtil.notificationTemplateDemo("title", "4567892", "5588");
        TransmissionTemplate transmissionTemplate = gexinUtil.transmissionTemplateDemo("445");
        gexinUtil.pushMessageToApp(notificationTemplate);
    }

    @Test
    public void single(){
        NotificationTemplate notificationTemplate = gexinUtil.notificationTemplateDemo("34", "778", "111");
        gexinUtil.pushMessageToSingle(notificationTemplate, "d171d29558072a9a50289197c1a1934a");

    }

    @Test
    public void pushWhenAddAd(){
        NotificationTemplate notificationTemplate = gexinUtil.notificationTemplateDemo("有新的宣传位", "address", "ad");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation","msg");
        TransmissionTemplate transmissionTemplate = gexinUtil.transmissionTemplateDemo(jsonObject.toString());

        gexinUtil.pushMessageToSingle(transmissionTemplate,"d171d29558072a9a50289197c1a1934a");
    }

    @Test
    public void pushWhenAddMsg(){
        NotificationTemplate te = gexinUtil.notificationTemplateDemo("您有新消息", "msg", "msg");
        gexinUtil.pushMessageToSingle(te, "d171d29558072a9a50289197c1a1934a");
    }

    @Test
    public void addMsg(){
        iMsgService.addMsg("cb0e511b-dd8e-11e8-b1eb-00163e04015b","newwwww","","1");
    }
}