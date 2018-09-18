package com.hjh.controller;

import com.yqh.util.common.BaseController;
import com.yqh.util.common.EmptyUtils;
import com.yqh.util.common.YqhException;
import com.yqh.util.common.enums.BaseMessageEnum;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 11:35 2018/9/18
 */
public class MyBaseController extends BaseController{
    @Override
    public void checkNecessaryParameter(String parameterName, Object parameter) throws YqhException {
        if (EmptyUtils.isEmpty(parameter)){
            throw new YqhException(BaseMessageEnum.PARATEMER_ERROR,"请填写"+parameterName);
        }
    }
}
