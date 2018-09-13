package com.hjh.service.impl;

import com.hjh.entity.Msg;
import com.hjh.dao.MsgDao;
import com.hjh.service.IMsgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgDao, Msg> implements IMsgService {
	
}
