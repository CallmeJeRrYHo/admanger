package com.hjh.service.impl;

import com.hjh.entity.Company;
import com.hjh.dao.CompanyDao;
import com.hjh.service.ICompanyService;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements ICompanyService {
	
}
