package com.hjh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjh.constant.Constant;
import com.hjh.dao.UserCompanyDao;
import com.hjh.entity.Company;
import com.hjh.dao.CompanyDao;
import com.hjh.entity.TUser;
import com.hjh.service.ICompanyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjh.service.ITUserService;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.ResultInfoUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements ICompanyService {

    @Autowired
    CompanyDao companyDao;
    @Autowired
    UserCompanyDao userCompanyDao;
    @Override
    public String addCompany(String userId, String companyName) {
        //用户是否为监管者
        checkIsAdmin(userId);
        Integer num = companyDao.selectCount(new EntityWrapper<Company>()
                .eq("company_name", companyName)
                .eq("status", Constant.STATUS_NORMAL));
        if (num > 0) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "公司名字已存在");
        }
        Company company = new Company();
        company.setCompanyId(UUID.randomUUID().toString());
        company.setCompanyName(companyName);
        company.setStatus(Constant.STATUS_NORMAL);
        company.insert();
        return ResultInfoUtils.infoData();
    }

    private void checkIsAdmin(String userId) {
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (3 != tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "该用户不为管理員");
        }
    }

    public void checkIsMonitor(String userId){
        TUser tUser = new TUser();
        tUser.setUserId(userId);
        tUser = tUser.selectById();
        if (2 != tUser.getUserType()&&3!= tUser.getUserType()) {
            throw new YqhException(BaseMessageEnum.UNKNOW_ERROR, "该用户不为监管者");
        }
    }
    @Override
    public String updateCompany(String userId, String companyId, String companyName) {
        checkIsMonitor(userId);
        Company company=new Company();
        company.setCompanyId(companyId);
        company.setCompanyName(companyName);
        company.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String deleteCompany(String userId, String companyId) {
        checkIsMonitor(userId);
        Company company=new Company();
        company.setCompanyId(companyId);
        company.setStatus(Constant.STATUS_DELETE);
        company.updateById();
        return ResultInfoUtils.infoData();
    }

    @Override
    public String selectMyCompany(Integer index, Integer pageSize, String userId, String companyId, String keyWord) {
        TUser tUser=new TUser();
        tUser.setUserId(userId);
        tUser=tUser.selectById();
        if (EmptyUtils.isEmpty(tUser)){
            throw new YqhException(BaseMessageEnum.USER_NOT_EXIST);
        }
        if (3==tUser.getUserType()){
            return selectCompany(index,pageSize,keyWord);
        }else {
            List<Company> companies = userCompanyDao.selectMyCompany(userId, companyId, keyWord);
            return ResultInfoUtils.infoData(companies);
        }
    }

    @Override
    public String selectCompany(Integer index, Integer pageSize, String keyWord) {
        Company company = new Company();
        Wrapper<Company> wrapper = new EntityWrapper<Company>()
                .eq("status", Constant.STATUS_NORMAL)
                .orderBy("create_time", false);
        if (EmptyUtils.isNotEmpty(keyWord)){
            wrapper=wrapper.like("company_name","%"+keyWord+"%");
        }
        Page<Company> status = company.selectPage(new Page<Company>(index, pageSize), wrapper);
        return ResultInfoUtils.infoData(status.getRecords(), status.getTotal());
    }

}
