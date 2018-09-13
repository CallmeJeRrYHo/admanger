package com.hjh.entity;

import java.math.BigDecimal;
import java.util.Date;
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
public class Advertisement extends Model<Advertisement> {

    private static final long serialVersionUID = 1L;

    @TableId("advertisement_id")
	private String advertisementId;
    /**
     * 管理公司id
     */
	@TableField("company_id")
	private String companyId;
    /**
     * 管理负责人id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 经度
     */
	private BigDecimal lontitude;
    /**
     * 纬度
     */
	private BigDecimal latitude;
    /**
     * 序号
     */
	@TableField("serial_num")
	private String serialNum;
    /**
     * 广告类型（样式） 1普通 2T型 3灯箱 4宣传栏
     */
	@TableField("ad_type")
	private Integer adType;
    /**
     * 广告牌位置
     */
	private String address;
    /**
     * 广告规格 1单面 2双面
     */
	@TableField("ad_spec")
	private Integer adSpec;
    /**
     * 广告排内容
     */
	@TableField("ad_content")
	private String adContent;
    /**
     * 是否放置国家领导人头像 1有 2无
     */
	@TableField("has_leader_portrait")
	private Integer hasLeaderPortrait;
    /**
     * 备注
     */
	private String remark;
    /**
     * 审核反馈（不通过时需要填写）
     */
	@TableField("audit_response")
	private String auditResponse;
    /**
     * 1待审核设计2设计不通过3待审核实景（设计通过）4实景不通过  5审核通过
     */
	@TableField("ad_status")
	private Integer adStatus;
	private Integer status;
	@TableField("modify_time")
	private Date modifyTime;
	@TableField("create_time")
	private Date createTime;


	public String getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(String advertisementId) {
		this.advertisementId = advertisementId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getLontitude() {
		return lontitude;
	}

	public void setLontitude(BigDecimal lontitude) {
		this.lontitude = lontitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAdSpec() {
		return adSpec;
	}

	public void setAdSpec(Integer adSpec) {
		this.adSpec = adSpec;
	}

	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}

	public Integer getHasLeaderPortrait() {
		return hasLeaderPortrait;
	}

	public void setHasLeaderPortrait(Integer hasLeaderPortrait) {
		this.hasLeaderPortrait = hasLeaderPortrait;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditResponse() {
		return auditResponse;
	}

	public void setAuditResponse(String auditResponse) {
		this.auditResponse = auditResponse;
	}

	public Integer getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(Integer adStatus) {
		this.adStatus = adStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.advertisementId;
	}

}
