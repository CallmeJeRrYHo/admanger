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
@TableName("msg_company")
public class MsgCompany extends Model<MsgCompany> {

    private static final long serialVersionUID = 1L;

    @TableId("msg_company_id")
	private String msgCompanyId;
	@TableField("msg_id")
	private String msgId;
	@TableField("company_id")
	private String companyId;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getMsgCompanyId() {
		return msgCompanyId;
	}

	public void setMsgCompanyId(String msgCompanyId) {
		this.msgCompanyId = msgCompanyId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
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
		return this.msgCompanyId;
	}

}
