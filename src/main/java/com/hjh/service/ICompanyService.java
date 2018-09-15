package com.hjh.service;

import com.hjh.entity.Company;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public interface ICompanyService extends IService<Company> {

    String addCompany(String userId, String companyName);

    String updateCompany(String userId, String companyId, String companyName);
}
