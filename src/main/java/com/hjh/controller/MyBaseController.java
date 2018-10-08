package com.hjh.controller;

import com.hjh.utils.BaseController;
import com.hjh.utils.EmptyUtils;
import com.hjh.utils.YqhException;
import com.hjh.utils.BaseMessageEnum;

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
