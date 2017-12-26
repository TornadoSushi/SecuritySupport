package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jerry on 2017/11/19.
 */

public class CheckItemRecord extends DataSupport implements Serializable {

    private int id;
    private int planId;
    private int companyCode;
    private String companyName;
    private int checkResult; //0 , 1 , 2 无隐患  一般隐患  重大隐患
    private int superItemId;
    private String superItemName;
    private int subItemId;
    private String subItemName;
    private String dangerDescri;//隐患描述
    private String sceneAdvice;//整改建议
    private String sceneMoney;//现场处罚金额
    private int lowPunish;//0 , 1 , 2 不执行 简易程序 一般程序
    private String lowForce;//强制行政措施
    private int isFinished;
    private String excuteTime;
    private String excutePerson;
    private int isReview;//是否复查

    public int getIsReview() {
        return isReview;
    }

    public void setIsReview(int isReview) {
        this.isReview = isReview;
    }

    @Override
    public String toString() {
        return "CheckItemRecord{" +
                "id=" + id +
                ", planId=" + planId +
                ", companyCode=" + companyCode +
                ", companyName='" + companyName + '\'' +
                ", checkResult=" + checkResult +
                ", superItemId=" + superItemId +
                ", superItemName='" + superItemName + '\'' +
                ", subItemId=" + subItemId +
                ", subItemName='" + subItemName + '\'' +
                ", dangerDescri='" + dangerDescri + '\'' +
                ", sceneAdvice='" + sceneAdvice + '\'' +
                ", sceneMoney='" + sceneMoney + '\'' +
                ", lowPunish=" + lowPunish +
                ", lowForce='" + lowForce + '\'' +
                ", isFinished=" + isFinished +
                ", excuteTime='" + excuteTime + '\'' +
                ", excutePerson='" + excutePerson + '\'' +
                '}';
    }

    public String getExcutePerson() {
        return excutePerson;
    }

    public void setExcutePerson(String excutePerson) {
        this.excutePerson = excutePerson;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getDangerDescri() {
        return dangerDescri;
    }

    public void setDangerDescri(String dangerDescri) {
        this.dangerDescri = dangerDescri;
    }

    public String getSceneAdvice() {
        return sceneAdvice;
    }

    public void setSceneAdvice(String sceneAdvice) {
        this.sceneAdvice = sceneAdvice;
    }

    public String getSceneMoney() {
        return sceneMoney;
    }

    public void setSceneMoney(String sceneMoney) {
        this.sceneMoney = sceneMoney;
    }

    public int getLowPunish() {
        return lowPunish;
    }

    public void setLowPunish(int lowPunish) {
        this.lowPunish = lowPunish;
    }

    public String getLowForce() {
        return lowForce;
    }

    public void setLowForce(String lowForce) {
        this.lowForce = lowForce;
    }
}
