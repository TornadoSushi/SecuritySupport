package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanyBaseInfo extends DataSupport {

    private int id;
    private int coalCode;
    private String coalLicence;
    private String coalLicLife;
    private String busiLicCode;
    private String saftyLicCode;
    private String miningLicCode;
    private String masCertiCode;
    private String coalLicCode;
    private String coalLicPrintState;
    private String coalLicState;
    private String coalLicDistri;
    private String busiLicState;
    private String miningLicState;
    private String miningLicDistri;
    private String masCertiState;
    private String saftyLicState;
    private String coalLicBelong;
    private String busiLicLife;
    private String busiLicBelong;
    private String miningLicLife;
    private String miningLicBelon;
    private String masCertiLife;
    private String masCertiBelong;
    private String saftyLicLife;
    private String saftyLicBelong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoalCode() {
        return coalCode;
    }

    public void setCoalCode(int coalCode) {
        this.coalCode = coalCode;
    }

    public String getCoalLicence() {
        return coalLicence;
    }

    public void setCoalLicence(String coalLicence) {
        this.coalLicence = coalLicence;
    }

    public String getCoalLicLife() {
        return coalLicLife;
    }

    public void setCoalLicLife(String coalLicLife) {
        this.coalLicLife = coalLicLife;
    }

    public String getBusiLicCode() {
        return busiLicCode;
    }

    public void setBusiLicCode(String busiLicCode) {
        this.busiLicCode = busiLicCode;
    }

    public String getSaftyLicCode() {
        return saftyLicCode;
    }

    public void setSaftyLicCode(String saftyLicCode) {
        this.saftyLicCode = saftyLicCode;
    }

    public String getMiningLicCode() {
        return miningLicCode;
    }

    public void setMiningLicCode(String miningLicCode) {
        this.miningLicCode = miningLicCode;
    }

    public String getMasCertiCode() {
        return masCertiCode;
    }

    public void setMasCertiCode(String masCertiCode) {
        this.masCertiCode = masCertiCode;
    }

    public String getCoalLicCode() {
        return coalLicCode;
    }

    public void setCoalLicCode(String coalLicCode) {
        this.coalLicCode = coalLicCode;
    }

    public String getCoalLicPrintState() {
        return coalLicPrintState;
    }

    public void setCoalLicPrintState(String coalLicPrintState) {
        this.coalLicPrintState = coalLicPrintState;
    }

    public String getCoalLicState() {
        return coalLicState;
    }

    public void setCoalLicState(String coalLicState) {
        this.coalLicState = coalLicState;
    }

    public String getCoalLicDistri() {
        return coalLicDistri;
    }

    public void setCoalLicDistri(String coalLicDistri) {
        this.coalLicDistri = coalLicDistri;
    }

    public String getBusiLicState() {
        return busiLicState;
    }

    public void setBusiLicState(String busiLicState) {
        this.busiLicState = busiLicState;
    }

    public String getMiningLicState() {
        return miningLicState;
    }

    public void setMiningLicState(String miningLicState) {
        this.miningLicState = miningLicState;
    }

    public String getMiningLicDistri() {
        return miningLicDistri;
    }

    public void setMiningLicDistri(String miningLicDistri) {
        this.miningLicDistri = miningLicDistri;
    }

    public String getMasCertiState() {
        return masCertiState;
    }

    public void setMasCertiState(String masCertiState) {
        this.masCertiState = masCertiState;
    }

    public String getSaftyLicState() {
        return saftyLicState;
    }

    public void setSaftyLicState(String saftyLicState) {
        this.saftyLicState = saftyLicState;
    }

    public String getCoalLicBelong() {
        return coalLicBelong;
    }

    public void setCoalLicBelong(String coalLicBelong) {
        this.coalLicBelong = coalLicBelong;
    }

    public String getBusiLicLife() {
        return busiLicLife;
    }

    public void setBusiLicLife(String busiLicLife) {
        this.busiLicLife = busiLicLife;
    }

    public String getBusiLicBelong() {
        return busiLicBelong;
    }

    public void setBusiLicBelong(String busiLicBelong) {
        this.busiLicBelong = busiLicBelong;
    }

    public String getMiningLicLife() {
        return miningLicLife;
    }

    public void setMiningLicLife(String miningLicLife) {
        this.miningLicLife = miningLicLife;
    }

    public String getMiningLicBelon() {
        return miningLicBelon;
    }

    public void setMiningLicBelon(String miningLicBelon) {
        this.miningLicBelon = miningLicBelon;
    }

    public String getMasCertiLife() {
        return masCertiLife;
    }

    public void setMasCertiLife(String masCertiLife) {
        this.masCertiLife = masCertiLife;
    }

    public String getMasCertiBelong() {
        return masCertiBelong;
    }

    public void setMasCertiBelong(String masCertiBelong) {
        this.masCertiBelong = masCertiBelong;
    }

    public String getSaftyLicLife() {
        return saftyLicLife;
    }

    public void setSaftyLicLife(String saftyLicLife) {
        this.saftyLicLife = saftyLicLife;
    }

    public String getSaftyLicBelong() {
        return saftyLicBelong;
    }

    public void setSaftyLicBelong(String saftyLicBelong) {
        this.saftyLicBelong = saftyLicBelong;
    }

    @Override
    public String toString() {
        return "CompanyBaseInfo{" +
                "id=" + id +
                ", coalCode=" + coalCode +
                ", coalLicence='" + coalLicence + '\'' +
                ", coalLicLife='" + coalLicLife + '\'' +
                ", busiLicCode='" + busiLicCode + '\'' +
                ", saftyLicCode='" + saftyLicCode + '\'' +
                ", miningLicCode='" + miningLicCode + '\'' +
                ", masCertiCode='" + masCertiCode + '\'' +
                ", coalLicCode='" + coalLicCode + '\'' +
                ", coalLicPrintState='" + coalLicPrintState + '\'' +
                ", coalLicState='" + coalLicState + '\'' +
                ", coalLicDistri='" + coalLicDistri + '\'' +
                ", busiLicState='" + busiLicState + '\'' +
                ", miningLicState='" + miningLicState + '\'' +
                ", miningLicDistri='" + miningLicDistri + '\'' +
                ", masCertiState='" + masCertiState + '\'' +
                ", saftyLicState='" + saftyLicState + '\'' +
                ", coalLicBelong='" + coalLicBelong + '\'' +
                ", busiLicLife='" + busiLicLife + '\'' +
                ", busiLicBelong='" + busiLicBelong + '\'' +
                ", miningLicLife='" + miningLicLife + '\'' +
                ", miningLicBelon='" + miningLicBelon + '\'' +
                ", masCertiLife='" + masCertiLife + '\'' +
                ", masCertiBelong='" + masCertiBelong + '\'' +
                ", saftyLicLife='" + saftyLicLife + '\'' +
                ", saftyLicBelong='" + saftyLicBelong + '\'' +
                '}';
    }
}
