package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/15.
 */

public class PlanCheckedItem extends DataSupport {

    private int id;
    private int planId;
    private int superId;
    private int subId;
    private int type;
    private int isAllFinish;

    public int getIsAllFinish() {
        return isAllFinish;
    }

    public void setIsAllFinish(int isAllFinish) {
        this.isAllFinish = isAllFinish;
    }

    @Override
    public String toString() {
        return "PlanCheckedItem{" +
                "id=" + id +
                ", planId=" + planId +
                ", superId=" + superId +
                ", subId=" + subId +
                ", type=" + type +
                ", isAllFinish=" + isAllFinish +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
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

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }
}
