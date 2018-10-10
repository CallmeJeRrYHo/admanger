package com.hjh.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 20:02 2018/9/14
 */
@Data
public class AdWarningWithPic {
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

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String createUserName;
    @TableField(exist = false)
    private String serialNum;
    @TableField(exist = false)
    private String address;
    @TableField(exist = false)
    private List<PicFile> picFiles;
    @TableField(exist = false)
    private List<WarningHandle>  warningHandles;
    /**
     * 是否显示处理框 ，1是 0否
     */
    @TableField(exist = false)
    private Integer isShowHandle;
    /**
     * 是否显示处理审核框 ，1是 0否
     */
    @TableField(exist = false)
    private Integer isShowHandleAudit;
    @TableField(exist = false)
    private String readStatus;
}
