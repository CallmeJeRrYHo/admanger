package com.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-10-03
 */
@TableName("user_company")
public class UserCompany extends Model<UserCompany> {

    private static final long serialVersionUID = 1L;

    @TableId("user_company_id")
	private String userCompanyId;
	@TableField("user_id")
	private String userId;
	@TableField("company_id")
	private String companyId;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getUserCompanyId() {
		return userCompanyId;
	}

	public void setUserCompanyId(String userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.userCompanyId;
	}

}
