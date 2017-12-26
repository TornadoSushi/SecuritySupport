package com.infinity.jerry.securitysupport.coal_security.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/27.
 */

public class BeBetterAdvice extends DataSupport {

    private int id;
    private int planId;

    private String inspItem;
    private String name;
    private String telling;
    private String baosong;

    private int companyId;
    private int type;
    private int subItemId;

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    @Override
    public String toString() {
        return "BeBetterAdvice{" +
                "id=" + id +
                ", planId=" + planId +
                ", inspItem='" + inspItem + '\'' +
                ", name='" + name + '\'' +
                ", telling='" + telling + '\'' +
                ", baosong='" + baosong + '\'' +
                ", companyId=" + companyId +
                ", type=" + type +
                '}';
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelling() {
        return telling;
    }

    public void setTelling(String telling) {
        this.telling = telling;
    }

    public String getBaosong() {
        return baosong;
    }

    public void setBaosong(String baosong) {
        this.baosong = baosong;
    }
}
