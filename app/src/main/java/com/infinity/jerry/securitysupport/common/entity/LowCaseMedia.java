package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/28.
 */

public class LowCaseMedia extends DataSupport {

    private int id;
    private int caseId;
    private int stepId;
    private String descri;
    private int docType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }
}
