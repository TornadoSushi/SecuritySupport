package com.infinity.jerry.securitysupport.common.entity;

/**
 * Created by jerry on 2017/11/27.
 */

public class CaseHelper {

    private String stepName;
    private int caseStep;
    private boolean isPass;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getCaseStep() {
        return caseStep;
    }

    public void setCaseStep(int caseStep) {
        this.caseStep = caseStep;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }
}
