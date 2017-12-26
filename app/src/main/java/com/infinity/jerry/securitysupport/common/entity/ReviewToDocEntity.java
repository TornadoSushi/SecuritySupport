package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/24.
 */

public class ReviewToDocEntity extends DataSupport {

    private int id;
    private int planId;
    private int subItemId;
    private int docType;
    private int docId;
    private String docName;

    @Override
    public String toString() {
        return "ReviewToDocEntity{" +
                "id=" + id +
                ", planId=" + planId +
                ", subItemId=" + subItemId +
                ", docType=" + docType +
                ", docId=" + docId +
                ", docName='" + docName + '\'' +
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

    public int getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(int subItemId) {
        this.subItemId = subItemId;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
