package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.google.gson.JsonSyntaxException;

import net.sf.json.JSONException;

public class BaseController {
    public BaseController() {
    }

    public String handleError(Exception e) {
        if (EmptyUtils.isEmpty(e)) {
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), "", e);
        } else if (e instanceof YqhException) {
            return ResultInfoUtils.errorData(((YqhException)e).getErrorCode().getCode(), ((YqhException)e).getErrorMsg(), e);
        } else if (!(e instanceof JSONException) && !(e instanceof JsonSyntaxException)) {
            e.printStackTrace();
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), e.getMessage(), e);
        } else {
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), "json格式有误", e);
        }
    }

    public String handleError(Exception e, String msg) {
        if (EmptyUtils.isEmpty(e)) {
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), "", e);
        } else if (e instanceof YqhException) {
            return ResultInfoUtils.errorData(((YqhException)e).getErrorCode().getCode(), ((YqhException)e).getErrorMsg(), e);
        } else if (!(e instanceof JSONException) && !(e instanceof JsonSyntaxException)) {
            e.printStackTrace();
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), msg, e);
        } else {
            return ResultInfoUtils.errorData(BaseMessageEnum.UNKNOW_ERROR.getCode(), "json格式有误", e);
        }
    }
    public void checkNecessaryParameter(String parameterName, Object parameter) throws YqhException {
        if (EmptyUtils.isEmpty(parameter)) {
            throw new YqhException(BaseMessageEnum.IS_NOT, parameterName + "为空");
        }
    }
}
