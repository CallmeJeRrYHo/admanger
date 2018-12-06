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
 * @since 2018-12-06
 */
@TableName("patrol_record")
public class PatrolRecord extends Model<PatrolRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 巡查记录id
     */
    @TableId("patrol_record_id")
	private String patrolRecordId;
    /**
     * 是否有问题 0没有 1有
     */
	@TableField("has_problem")
	private Integer hasProblem;
	@TableField("user_id")
	private String userId;

	@TableField("advertisement_id")
	private String advertisementId;
    /**
     * 是否报警 0没有 1有
     */
	@TableField("is_warning")
	private Integer isWarning;
    /**
     * 报警编号
     */
	@TableField("warning_no")
	private String warningNo;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;
	private Integer status;

	public PatrolRecord() {
	}


	public String getPatrolRecordId() {
		return patrolRecordId;
	}

	public void setPatrolRecordId(String patrolRecordId) {
		this.patrolRecordId = patrolRecordId;
	}

	public Integer getHasProblem() {
		return hasProblem;
	}

	public void setHasProblem(Integer hasProblem) {
		this.hasProblem = hasProblem;
	}

	public Integer getIsWarning() {
		return isWarning;
	}

	public void setIsWarning(Integer isWarning) {
		this.isWarning = isWarning;
	}

	public String getWarningNo() {
		return warningNo;
	}

	public void setWarningNo(String warningNo) {
		this.warningNo = warningNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.patrolRecordId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(String advertisementId) {
		this.advertisementId = advertisementId;
	}
}
