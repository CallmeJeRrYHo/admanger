package com.hjh.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hjh
 * @since 2018-09-12
 */
@TableName("pic_file")
@Data
public class PicFile extends Model<PicFile> {

    private static final long serialVersionUID = 1L;

    @TableId("pic_id")
	@JsonProperty("pic_id")
	private String picId;
    /**
     * 图片路径
     */
	private String path;
    /**
     * 类型1 头像 2广告牌设计稿 3实景图片
     */
	private Integer type;
    /**
     * 关联项id
     */
	@TableField("busness_id")
	private String busnessId;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	@Override
	protected Serializable pkVal() {
		return this.picId;
	}

}
