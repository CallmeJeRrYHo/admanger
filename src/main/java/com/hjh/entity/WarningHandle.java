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
@TableName("warning_handle")
public class WarningHandle extends Model<WarningHandle> {

    private static final long serialVersionUID = 1L;

    /**
     * 报警处理id
     */
    @TableId("handle_id")
	private String handleId;
    /**
     * 报警id
     */
	@TableField("warning_id")
	private String warningId;
    /**
     * 出列结果
     */
	private String content;
    /**
     * 处理标题
     */
	private String title;
    /**
     * 处理回复（主要是不通过的理由）
     */
	@TableField("handle_response")
	private String handleResponse;
    /**
     * 处理状态1待审核 2已通过 3已驳回
     */
	@TableField("handle_status")
	private Integer handleStatus;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getHandleId() {
		return handleId;
	}

	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}

	public String getWarningId() {
		return warningId;
	}

	public void setWarningId(String warningId) {
		this.warningId = warningId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHandleResponse() {
		return handleResponse;
	}

	public void setHandleResponse(String handleResponse) {
		this.handleResponse = handleResponse;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
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
		return this.handleId;
	}

}
