package com.hjh.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
public class Company extends Model<Company> {

    private static final long serialVersionUID = 1L;

    @TableId("company_id")
	private String companyId;
    /**
     * 公司名
     */
	@TableField("company_name")
	private String companyName;


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	protected Serializable pkVal() {
		return this.companyId;
	}

}
