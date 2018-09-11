package com.hjh.service.impl;

import com.hjh.entity.TUser;
import com.hjh.dao.TUserDao;
import com.hjh.service.ITUserService;
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
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUser> implements ITUserService {
	
}
