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
@TableName("msg_read")
public class MsgRead extends Model<MsgRead> {

    private static final long serialVersionUID = 1L;

    @TableId("msg_read_id")
	private String msgReadId;
	@TableField("msg_id")
	private String msgId;
	@TableField("user_id")
	private String userId;
	@TableField("create_time")
	private Date createTime;


	public String getMsgReadId() {
		return msgReadId;
	}

	public void setMsgReadId(String msgReadId) {
		this.msgReadId = msgReadId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.msgReadId;
	}

}
