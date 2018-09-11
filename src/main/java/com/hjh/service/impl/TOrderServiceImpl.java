package com.hjh.service.impl;

import com.hjh.entity.TOrder;
import com.hjh.dao.TOrderDao;
import com.hjh.service.ITOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-11
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderDao, TOrder> implements ITOrderService {
	@Autowired
    TOrderDao tOrderDao;
}
