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
 * @since 2018-09-12
 */
@TableName("ad_warning")
public class AdWarning extends Model<AdWarning> {

    private static final long serialVersionUID = 1L;

    /**
     * 报警id
     */
    @TableId("warning_id")
	private String warningId;
    /**
     * 创建用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 报警内容
     */
	private String content;
    /**
     * 相关广告牌
     */
	@TableField("advertisememt_id")
	private String advertisememtId;
    /**
     * 报警标题
     */
	private String title;
    /**
     * 报警状态，1待处理，2已完成
     */
	@TableField("warning_status")
	private Integer warningStatus;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getWarningId() {
		return warningId;
	}

	public void setWarningId(String warningId) {
		this.warningId = warningId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdvertisememtId() {
		return advertisememtId;
	}

	public void setAdvertisememtId(String advertisememtId) {
		this.advertisememtId = advertisememtId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWarningStatus() {
		return warningStatus;
	}

	public void setWarningStatus(Integer warningStatus) {
		this.warningStatus = warningStatus;
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
		return this.warningId;
	}

}
