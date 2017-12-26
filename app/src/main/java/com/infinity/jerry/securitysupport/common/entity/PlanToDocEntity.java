package com.infinity.jerry.securitysupport.common.entity;


import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/23.
 */

public class PlanToDocEntity extends DataSupport {

    private int id;
    private int planId;
    private int docType;
    private int docId;
    private String docName;

    @Override
    public String toString() {
        return "PlanToDocEntity{" +
                "id=" + id +
                ", planId=" + planId +
                ", docType=" + docType +
                ", docId=" + docId +
                ", docName=" + docName +
                ", docBelong=" + docBelong +
                '}';
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    private int docBelong;//现场检查 复查

    public int getDocBelong() {
        return docBelong;
    }

    public void setDocBelong(int docBelong) {
        this.docBelong = docBelong;
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
}
