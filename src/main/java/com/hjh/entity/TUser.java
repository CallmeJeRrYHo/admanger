package com.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */

@TableName("t_user")
public class TUser extends Model<TUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId("user_id")
	private String userId;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 名字
     */
	private String name;
    /**
     * 头像url
     */
	@TableField("portrait_url")
	private String portraitUrl;


	/**
     * 密码
     */
    @JsonProperty(required = false)
	@JsonIgnore
	private String password;
    /**
     * 用户类型 1管理者 2监管者
     */
	@TableField("user_type")
	private Integer userType;
    /**
     * 公司企业id 
     */
	@TableField("company_id")
	private String companyId;
    /**
     * 上级id 角色为管理者的时候不为空
     */
	@TableField("superior_id")
	private String superiorId;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;

	@TableField(exist = false)
	private String superiorName;
	@TableField(exist = false)
	private String companyName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
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

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
