package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanyShaft extends DataSupport {


    @Override
    public String toString() {
        return "CompanyShaft{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", shaftArea='" + shaftArea + '\'' +
                ", shaftSize='" + shaftSize + '\'' +
                ", shaftCan='" + shaftCan + '\'' +
                ", shaftCount='" + shaftCount + '\'' +
                ", shaftHeight='" + shaftHeight + '\'' +
                ", shaftLic='" + shaftLic + '\'' +
                ", shaftDeepA='" + shaftDeepA + '\'' +
                ", shaftDeepB='" + shaftDeepB + '\'' +
                '}';
    }


    private int id;
    private int companyCode;
    private String shaftArea;
    private String shaftSize;
    private String shaftCan;
    private String shaftCount;
    private String shaftHeight;
    private String shaftLic;
    private String shaftDeepA;
    private String shaftDeepB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public String getShaftArea() {
        return shaftArea;
    }

    public void setShaftArea(String shaftArea) {
        this.shaftArea = shaftArea;
    }

    public String getShaftSize() {
        return shaftSize;
    }

    public void setShaftSize(String shaftSize) {
        this.shaftSize = shaftSize;
    }

    public String getShaftCan() {
        return shaftCan;
    }

    public void setShaftCan(String shaftCan) {
        this.shaftCan = shaftCan;
    }

    public String getShaftCount() {
        return shaftCount;
    }

    public void setShaftCount(String shaftCount) {
        this.shaftCount = shaftCount;
    }

    public String getShaftHeight() {
        return shaftHeight;
    }

    public void setShaftHeight(String shaftHeight) {
        this.shaftHeight = shaftHeight;
    }

    public String getShaftLic() {
        return shaftLic;
    }

    public void setShaftLic(String shaftLic) {
        this.shaftLic = shaftLic;
    }

    public String getShaftDeepA() {
        return shaftDeepA;
    }

    public void setShaftDeepA(String shaftDeepA) {
        this.shaftDeepA = shaftDeepA;
    }

    public String getShaftDeepB() {
        return shaftDeepB;
    }

    public void setShaftDeepB(String shaftDeepB) {
        this.shaftDeepB = shaftDeepB;
    }
}
