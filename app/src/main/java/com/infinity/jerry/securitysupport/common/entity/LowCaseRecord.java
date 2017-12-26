package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jerry on 2017/11/27.
 */

public class LowCaseRecord extends DataSupport implements Serializable {

    private int id;
    private int companyCode;
    private String companyName;
    //这个值很重要,每一次下移的时候都需要添加文书 描述等。与id pk 同时关联一个 文书记录表
    private int caseStep;
    private int superItemId;
    private String superItemName;
    private int subItemId;
    private String subItemName;
    private String excutePerson;
    private String excuteTime;
    private int isFinish;

    private int isSyn = 0;

    public int getIsSyn() {
        return isSyn;
    }

    public void setIsSyn(int isSyn) {
        this.isSyn = isSyn;
    }

    @Override
    public String toString() {
        return "LowCaseRecord{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", companyName='" + companyName + '\'' +
                ", caseStep=" + caseStep +
                ", superItemId=" + superItemId +
                ", superItemName='" + superItemName + '\'' +
                ", subItemId=" + subItemId +
                ", subItemName='" + subItemName + '\'' +
                ", excutePerson='" + excutePerson + '\'' +
                ", excuteTime='" + excuteTime + '\'' +
                ", isFinish=" + isFinish +
                '}';
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public int getCaseStep() {
        return caseStep;
    }

    public void setCaseStep(int caseStep) {
        this.caseStep = caseStep;
    }

    public int getSuperItemId() {
        return superItemId;
    }

    public void setSuperItemId(int superItemId) {
        this.superItemId = superItemId;
    }

    public String getSuperItemName() {
        return superItemName;
    }

    public void setSuperItemName(String superItemName) {
        this.superItemName = superItemName;
    }

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }

    public String getExcutePerson() {
        return excutePerson;
    }

    public void setExcutePerson(String excutePerson) {
        this.excutePerson = excutePerson;
    }
}


