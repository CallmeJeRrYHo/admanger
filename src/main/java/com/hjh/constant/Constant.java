package com.hjh.constant;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 23:30 2018/9/17
 */
public interface Constant {
    //正常
    Integer STATUS_NORMAL=1;
    //删除
    Integer STATUS_DELETE=-1;
    //待处理
    Integer WARING_WAIT_DEAL = 1;
    //已处理
    Integer WARING_FINISH = 2;

    //头像
    Integer PIC_PORTRAIT_PIC = 1;
    //设计图片
    Integer PIC_DESIGN_PIC = 2;
    //实景图片
    Integer PIC_LIVE_VIEW_PIC=3;
    //报警图片
    Integer PIC_WARING_PIC = 4;
    //公告图片
    Integer PIC_MSG_PIC=5;
    //报警处理图片
    Integer PIC_WARING_HANDLE_PIC = 6;
    //处理状态 待审核
    Integer HANDLE_STATUS_WAIT = 1;
    //已通过
    Integer HANDLE_STATUS_PASS = 2;
    //不通过
    Integer HANDLE_STATUS_NOT_PASS = 3;


    //广告状态待审核
    Integer AD_STATUS_WAIT_AUDIT = 1;
    //设计不通过
    Integer AD_STATUS_DESIGN_NOT_PASS = 2;

    //设计通过（带提交实景）
    Integer AD_STATUS_DESIGN_PASS = 3;

    //待审核实景
    Integer AD_STATUS_WAIT_AUDIT_LIVE_VIEW = 4;

    //审核通过
    Integer AD_STATUS_ALL_PASS = 5;

}
