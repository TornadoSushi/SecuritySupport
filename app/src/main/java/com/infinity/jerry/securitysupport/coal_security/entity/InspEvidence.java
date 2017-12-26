package com.infinity.jerry.securitysupport.coal_security.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/27.
 */

public class InspEvidence extends DataSupport {

    private int id;
    private int planId;
    private String inSpItem;
    private String startTime;
    private String endTime;
    private String location;
    private String content;
    //调查人信息
    private String name;
    private String gender;
    private String age;
    private String workPlace;
    private String duty;
    private String protical;
    private String level;
    private String phone;
    private String address;
    private String investMan;
    private String investRecord;

    private int companyId;
    private int recordType;

    @Override
    public String toString() {
        return "InspEvidence{" +
                "id=" + id +
                ", planId=" + planId +
                ", inSpItem='" + inSpItem + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", location='" + location + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", duty='" + duty + '\'' +
                ", protical='" + protical + '\'' +
                ", level='" + level + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", investMan='" + investMan + '\'' +
                ", investRecord='" + investRecord + '\'' +
                ", companyId=" + companyId +
                ", recordType=" + recordType +
                '}';
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getInSpItem() {
        return inSpItem;
    }

    public void setInSpItem(String inSpItem) {
        this.inSpItem = inSpItem;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getProtical() {
        return protical;
    }

    public void setProtical(String protical) {
        this.protical = protical;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvestMan() {
        return investMan;
    }

    public void setInvestMan(String investMan) {
        this.investMan = investMan;
    }

    public String getInvestRecord() {
        return investRecord;
    }

    public void setInvestRecord(String investRecord) {
        this.investRecord = investRecord;
    }
}
