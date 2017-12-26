package com.infinity.jerry.securitysupport.coal_security.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/9/28.
 */

public class ReViewAdviceDoc extends DataSupport {

    private int id;
    private String inspItem;
    private int planId;
    private int subItemId;
    //现场0 复查1 立案2 打印3
    private int type;
    private String advice;
    private String userName;

    @Override
    public String toString() {
        return "ReViewAdviceDoc{" +
                "id=" + id +
                ", inspItem='" + inspItem + '\'' +
                ", planId=" + planId +
                ", subItemId=" + subItemId +
                ", type=" + type +
                ", advice='" + advice + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInspItem() {
        return inspItem;
    }

    public void setInspItem(String inspItem) {
        this.inspItem = inspItem;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
