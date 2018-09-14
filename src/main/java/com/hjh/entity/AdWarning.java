package com.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-09-14
 */
@Data
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
     * 公司id
     */
	@TableField("company_id")
	private String companyId;
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
	@Override
	protected Serializable pkVal() {
		return this.warningId;
	}

}
