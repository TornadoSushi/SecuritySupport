package com.infinity.jerry.securitysupport.coal_security.entity;


import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/26.
 */

public class SceneInspEntity extends DataSupport {

    private int id;
    private int planId;
    private String inspItem;
    private String place;
    private long timeStamp;
    private String line;
    private String mining_lic;
    private String mas_safty_certi;
    private String mas_certi;
    private String satfy_lic;
    private String coal_lic;
    private String secondPerson;
    private String inspContent;
    private String inspAdvice;
    private String userName;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "SceneInspRecord{" +
                "id=" + id +
                ", planId=" + planId +
                ", inspItem='" + inspItem + '\'' +
                ", place='" + place + '\'' +
                ", timeStamp=" + timeStamp +
                ", line='" + line + '\'' +
                ", mining_lic='" + mining_lic + '\'' +
                ", mas_safty_certi='" + mas_safty_certi + '\'' +
                ", mas_certi='" + mas_certi + '\'' +
                ", satfy_lic='" + satfy_lic + '\'' +
                ", coal_lic='" + coal_lic + '\'' +
                ", secondPerson='" + secondPerson + '\'' +
                ", inspContent='" + inspContent + '\'' +
                ", inspAdvice='" + inspAdvice + '\'' +
                ", userName='" + userName + '\'' +
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

    public String getInspItem() {
        return inspItem;
    }

    public void setInspItem(String inspItem) {
        this.inspItem = inspItem;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMining_lic() {
        return mining_lic;
    }

    public void setMining_lic(String mining_lic) {
        this.mining_lic = mining_lic;
    }

    public String getMas_safty_certi() {
        return mas_safty_certi;
    }

    public void setMas_safty_certi(String mas_safty_certi) {
        this.mas_safty_certi = mas_safty_certi;
    }

    public String getMas_certi() {
        return mas_certi;
    }

    public void setMas_certi(String mas_certi) {
        this.mas_certi = mas_certi;
    }

    public String getSatfy_lic() {
        return satfy_lic;
    }

    public void setSatfy_lic(String satfy_lic) {
        this.satfy_lic = satfy_lic;
    }

    public String getCoal_lic() {
        return coal_lic;
    }

    public void setCoal_lic(String coal_lic) {
        this.coal_lic = coal_lic;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(String secondPerson) {
        this.secondPerson = secondPerson;
    }

    public String getInspContent() {
        return inspContent;
    }

    public void setInspContent(String inspContent) {
        this.inspContent = inspContent;
    }

    public String getInspAdvice() {
        return inspAdvice;
    }

    public void setInspAdvice(String inspAdvice) {
        this.inspAdvice = inspAdvice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
