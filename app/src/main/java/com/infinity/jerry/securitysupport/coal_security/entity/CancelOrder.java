package com.infinity.jerry.securitysupport.coal_security.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/27.
 */

public class CancelOrder extends DataSupport {

    private int id;
    private int planId;
    private String inspItem;
    private String content;
    private String area;
    private String result;

    private int type;
    private int companyId;
    private int subItemId;

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    @Override
    public String toString() {
        return "CancelOrder{" +
                "id=" + id +
                ", planId=" + planId +
                ", inspItem='" + inspItem + '\'' +
                ", content='" + content + '\'' +
                ", area='" + area + '\'' +
                ", result='" + result + '\'' +
                ", type=" + type +
                ", companyId=" + companyId +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getInspItem() {
        return inspItem;
    }

    public void setInspItem(String inspItem) {
        this.inspItem = inspItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
