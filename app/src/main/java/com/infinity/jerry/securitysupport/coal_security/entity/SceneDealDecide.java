package com.infinity.jerry.securitysupport.coal_security.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/27.
 */

public class SceneDealDecide extends DataSupport {

    private int id;
    private int planId;
    private String inspItem;
    private String time;
    private String illgalContent;
    private String dealContent;
    private String investName;
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    //复查
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
        return "SceneDealDecide{" +
                "id=" + id +
                ", planId=" + planId +
                ", inspItem='" + inspItem + '\'' +
                ", time='" + time + '\'' +
                ", illgalContent='" + illgalContent + '\'' +
                ", dealContent='" + dealContent + '\'' +
                ", investName='" + investName + '\'' +
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

    public void setType(int recordType) {
        this.type = recordType;
    }

    public String getInspItem() {
        return inspItem;
    }

    public void setInspItem(String inspItem) {
        this.inspItem = inspItem;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIllgalContent() {
        return illgalContent;
    }

    public void setIllgalContent(String illgalContent) {
        this.illgalContent = illgalContent;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getInvestName() {
        return investName;
    }

    public void setInvestName(String investName) {
        this.investName = investName;
    }
}
