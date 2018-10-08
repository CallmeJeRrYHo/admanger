package com.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@Data
public class Msg extends Model<Msg> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息di
     */
    @TableId("msg_id")
	private String msgId;
    /**
     * 消息标题
     */
	private String title;
    /**
     * 消息内容
     */
	private String content;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;
	@TableField("create_user_id")
	private String createUserId;
	@TableField(exist = false)
	private List<PicFile> picFiles;
	@TableField(exist = false)
	private String createUserName;
	@TableField(exist = false)
	private String readStatus;
	@Override
	protected Serializable pkVal() {
		return this.msgId;
	}

}
