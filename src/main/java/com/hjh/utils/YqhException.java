package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class YqhException extends RuntimeException {
    private BaseMessageInterface errorCode;
    private String errorMsg;

    public BaseMessageInterface getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(BaseMessageInterface errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public YqhException(BaseMessageInterface errorCode) {
        this.errorCode = errorCode;
        this.errorMsg = errorCode.getMessage();
    }

    public YqhException(BaseMessageInterface errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMsg = message;
    }
}
