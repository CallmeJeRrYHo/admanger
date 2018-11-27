package com.hjh.service.impl;

import com.gexin.rp.sdk.template.NotificationTemplate;
import com.hjh.utils.GexinUtil;
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
    @Test
    public void push(){
        NotificationTemplate notificationTemplate = gexinUtil.notificationTemplateDemo("title", "conttt", "111");
        gexinUtil.pushMessageToApp(notificationTemplate);
    }

    @Test
    public void single(){
        NotificationTemplate notificationTemplate = gexinUtil.notificationTemplateDemo("tissstle", "ssssss", "111");
        gexinUtil.pushMessageToSingle(notificationTemplate, "ccf122f4dcd0154c134be09727f8b1e0");

    }
}