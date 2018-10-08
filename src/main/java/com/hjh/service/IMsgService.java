package com.hjh.service;

import com.hjh.entity.Msg;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface IMsgService extends IService<Msg> {

    String addMsg(String userId, String content, String pics, String companyIds);

    String readMsg(String msgId, String userId);
}
