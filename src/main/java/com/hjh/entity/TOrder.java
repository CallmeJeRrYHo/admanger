package com.hjh.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author hjh
 * @since 2018-09-11
 */
@TableName("t_order")
public class TOrder extends Model<TOrder> {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
	private String orderId;
    /**
     * 创建人id
     */
	@TableField("create_user_id")
	private String createUserId;
    /**
     * 接单人id
     */
	@TableField("receive_user_id")
	private String receiveUserId;
    /**
     * 搬运工id
     */
	@TableField("work_user_id")
	private String workUserId;
    /**
     * 物流单号
     */
	@TableField("logistics_order_id")
	private String logisticsOrderId;
    /**
     * 确认码
     */
	@TableField("confirm_code")
	private String confirmCode;
	@TableField("start_province")
	private String startProvince;
	@TableField("start_city")
	private String startCity;
	@TableField("start_area")
	private String startArea;
	@TableField("start_address")
	private String startAddress;
	@TableField("receiver_name")
	private String receiverName;
	@TableField("sender_name")
	private String senderName;
	@TableField("sender_phone")
	private String senderPhone;
	@TableField("end_province")
	private String endProvince;
	@TableField("end_city")
	private String endCity;
	@TableField("end_area")
	private String endArea;
	@TableField("end_address")
	private String endAddress;
	@TableField("receiver_phone")
	private String receiverPhone;
	@TableField("good_name")
	private String goodName;
	@TableField("good_num")
	private Double goodNum;
	@TableField("package_name")
	private String packageName;
    /**
     * 1.按重量2.按体积
     */
	@TableField("cost_rule")
	private Integer costRule;
    /**
     * 重量或者体积数量
     */
	@TableField("cost_rule_num")
	private Double costRuleNum;
    /**
     * 0.不上楼1.有电梯2.无电梯
     */
	@TableField("upstairs_type")
	private Integer upstairsType;
    /**
     * 楼层数量
     */
	private Double floor;
    /**
     * 运输距离
     */
	@TableField("in_transport_distance")
	private Double inTransportDistance;
    /**
     * 水平距离
     */
	@TableField("horizontald_distance")
	private Double horizontaldDistance;
    /**
     * 回单数量
     */
	@TableField("feedback_num")
	private Integer feedbackNum;
    /**
     * 是否回寄1是2否
     */
	@TableField("is_feedback")
	private Integer isFeedback;
    /**
     * 回寄地址
     */
	@TableField("feedback_address")
	private String feedbackAddress;
    /**
     * 回寄备注
     */
	@TableField("feedback_remark")
	private String feedbackRemark;
    /**
     * 订单备注
     */
	private String remark;
    /**
     * 0.待支付1.待接单2.已接单3.进行中4.完成5.已取消
     */
	@TableField("receive_status")
	private Integer receiveStatus;
    /**
     * 追加总费用
     */
	@TableField("total_append_price")
	private BigDecimal totalAppendPrice;
    /**
     * 搬价
     */
	@TableField("move_price")
	private BigDecimal movePrice;
    /**
     * 上楼价格
     */
	@TableField("upstairs_price")
	private BigDecimal upstairsPrice;
    /**
     * 运费
     */
	private BigDecimal freight;
    /**
     * 订单总价
     */
	@TableField("order_total_price")
	private BigDecimal orderTotalPrice;
	private Integer status;
    /**
     * 完成时间
     */
	@TableField("complete_time")
	private Date completeTime;
    /**
     * 分配时间
     */
	@TableField("distribute_time")
	private Date distributeTime;
    /**
     * 支付时间
     */
	@TableField("pay_time")
	private Date payTime;
	@TableField("create_time")
	private Date createTime;
	@TableField("modify_time")
	private Date modifyTime;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getWorkUserId() {
		return workUserId;
	}

	public void setWorkUserId(String workUserId) {
		this.workUserId = workUserId;
	}

	public String getLogisticsOrderId() {
		return logisticsOrderId;
	}

	public void setLogisticsOrderId(String logisticsOrderId) {
		this.logisticsOrderId = logisticsOrderId;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public String getStartProvince() {
		return startProvince;
	}

	public void setStartProvince(String startProvince) {
		this.startProvince = startProvince;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStartArea() {
		return startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getEndProvince() {
		return endProvince;
	}

	public void setEndProvince(String endProvince) {
		this.endProvince = endProvince;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getEndArea() {
		return endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Double getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(Double goodNum) {
		this.goodNum = goodNum;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getCostRule() {
		return costRule;
	}

	public void setCostRule(Integer costRule) {
		this.costRule = costRule;
	}

	public Double getCostRuleNum() {
		return costRuleNum;
	}

	public void setCostRuleNum(Double costRuleNum) {
		this.costRuleNum = costRuleNum;
	}

	public Integer getUpstairsType() {
		return upstairsType;
	}

	public void setUpstairsType(Integer upstairsType) {
		this.upstairsType = upstairsType;
	}

	public Double getFloor() {
		return floor;
	}

	public void setFloor(Double floor) {
		this.floor = floor;
	}

	public Double getInTransportDistance() {
		return inTransportDistance;
	}

	public void setInTransportDistance(Double inTransportDistance) {
		this.inTransportDistance = inTransportDistance;
	}

	public Double getHorizontaldDistance() {
		return horizontaldDistance;
	}

	public void setHorizontaldDistance(Double horizontaldDistance) {
		this.horizontaldDistance = horizontaldDistance;
	}

	public Integer getFeedbackNum() {
		return feedbackNum;
	}

	public void setFeedbackNum(Integer feedbackNum) {
		this.feedbackNum = feedbackNum;
	}

	public Integer getIsFeedback() {
		return isFeedback;
	}

	public void setIsFeedback(Integer isFeedback) {
		this.isFeedback = isFeedback;
	}

	public String getFeedbackAddress() {
		return feedbackAddress;
	}

	public void setFeedbackAddress(String feedbackAddress) {
		this.feedbackAddress = feedbackAddress;
	}

	public String getFeedbackRemark() {
		return feedbackRemark;
	}

	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public BigDecimal getTotalAppendPrice() {
		return totalAppendPrice;
	}

	public void setTotalAppendPrice(BigDecimal totalAppendPrice) {
		this.totalAppendPrice = totalAppendPrice;
	}

	public BigDecimal getMovePrice() {
		return movePrice;
	}

	public void setMovePrice(BigDecimal movePrice) {
		this.movePrice = movePrice;
	}

	public BigDecimal getUpstairsPrice() {
		return upstairsPrice;
	}

	public void setUpstairsPrice(BigDecimal upstairsPrice) {
		this.upstairsPrice = upstairsPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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
		return this.orderId;
	}

}
