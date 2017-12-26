package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jerry on 2017/11/15.
 */
//执法计划
public class PlanRecord extends DataSupport implements Serializable {

    private int id;
    private String startTime;
    private String endTime;
    private int planType;//0 全面安全检查 1 全面卫生检查 2 临时安全检查 3 临时卫生检查
    private String planName;
    private int companyCode;
    private String companyName;
    private int isStart;
    private int isFinish;
    private int person1_id;
    private String excutePerson1;//执行人1名字
    private int person2_id;
    private String excutePerson2;//执行人2名字
    private String planScheme;

    public String getPlanScheme() {
        return planScheme;
    }

    public void setPlanScheme(String planScheme) {
        this.planScheme = planScheme;
    }

    private int isSyn;

    public void setIsSyn(int isSyn) {
        this.isSyn = isSyn;
    }

    public int getIsSyn() {
        return isSyn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPlanName(String planName) {

        this.planName = planName;
    }

    public String getPlanName() {
        return planName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getPerson1_id() {
        return person1_id;
    }

    public void setPerson1_id(int person1_id) {
        this.person1_id = person1_id;
    }

    public String getExcutePerson1() {
        return excutePerson1;
    }

    public void setExcutePerson1(String excutePerson1) {
        this.excutePerson1 = excutePerson1;
    }

    public int getPerson2_id() {
        return person2_id;
    }

    public void setPerson2_id(int person2_id) {
        this.person2_id = person2_id;
    }

    public String getExcutePerson2() {
        return excutePerson2;
    }

    public void setExcutePerson2(String excutePerson2) {
        this.excutePerson2 = excutePerson2;
    }


    @Override
    public String toString() {
        return "PlanRecord{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", planType=" + planType +
                ", planName='" + planName + '\'' +
                ", companyCode=" + companyCode +
                ", companyName='" + companyName + '\'' +
                ", isStart=" + isStart +
                ", isFinish=" + isFinish +
                ", person1_id=" + person1_id +
                ", excutePerson1='" + excutePerson1 + '\'' +
                ", person2_id=" + person2_id +
                ", excutePerson2='" + excutePerson2 + '\'' +
                '}';
    }
}
