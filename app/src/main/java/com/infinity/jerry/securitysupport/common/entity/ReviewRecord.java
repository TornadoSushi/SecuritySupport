package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/24.
 */

public class ReviewRecord extends DataSupport {

    private int id;
    private int planId;
    private int subItemId;
    private String advice;
    private String excuteTime;

    private int isSyn = 0;

    public int getIsSyn() {
        return isSyn;
    }

    public void setIsSyn(int isSyn) {
        this.isSyn = isSyn;
    }

    @Override
    public String toString() {
        return "ReviewRecord{" +
                "id=" + id +
                ", planId=" + planId +
                ", subItemId=" + subItemId +
                ", advice='" + advice + '\'' +
                ", excuteTime='" + excuteTime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }
}
