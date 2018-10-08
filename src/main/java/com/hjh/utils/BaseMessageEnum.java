package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.hjh.utils.BaseMessageInterface;

public enum BaseMessageEnum implements BaseMessageInterface {
    SUCCESS(0, "成功"),
    IS_NOT(1, "参数为空"),
    LOGIN_TIMEOUT(-1, "登录超时"),
    LOGIN_FAILL(2, "用户名或密码错误"),
    MOBILE_EXIST(3, "手机号码已存在"),
    MOBILE_NOT_EXIST(-3, "手机号码不存在"),
    NAME_EXIST(3, "用户名已存在"),
    UPLOAD_FAIL(22, "文件上传失败"),
    PICTURE_FAIL_SUFFIX(23, "文件上传格式错误"),
    PICTURE_FAIL_SIZE(24, "上传文件超出最大值20M"),
    GET_DETAILS_EXIST(25, "获取详情失败"),
    USER_NOT_EXIST(26, "用户不存在"),
    POSITION_EXISTENCE_ERROR(176, "该职位已被胜任"),
    PARATEMER_ERROR(177, "请输入正确手机号"),
    UPDATE_ERROR(178, "修改失败"),
    UNKNOW_ERROR(9999, "未知异常"),
    SEND_FREQUENCY_TOO_HIGH(9998, "短信发送频率太高"),
    ERROR_CHECKCODE(11, "验证码错误"),
    ERROR_PASSWORD(15, "旧密码错误"),
    REPEAT_PASSWORD(16, "新密码与旧密码相同!"),
    ERROR_RECOMMENDCODE(12, "推荐码错误"),
    SYS_NOT_ENOUGH(555, "系统余额不足"),
    OUT_OF_BALANCE(35, "数量不足"),
    WALLET_PASSWORD_ERROR(31, "钱包密码错误"),
    TARGET_ACCOUNT_NOT_EXIST(32, "目标账号不存在"),
    TARGET_CAN_NOT_BE_YOURSELF(33, "不能向自己账号进行操作"),
    NO_COIN_RATE(34, "暂无比率"),
    ALREADY_SIGN_IN(15658, "今日已签到"),
    ERROR_SYS_KEY(666, "系统密钥错误，请与管理员联系!"),
    ERROR_TOO_FREQUENT(9997, "该手机号发送短信太频繁，请稍后再试");

    private int retCode;
    private String retMess;

    private BaseMessageEnum(int retCode, String retMess) {
        this.retCode = retCode;
        this.retMess = retMess;
    }

    public int getCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return this.retMess;
    }

    public void setRetMess(String retMess) {
        this.retMess = retMess;
    }
}
