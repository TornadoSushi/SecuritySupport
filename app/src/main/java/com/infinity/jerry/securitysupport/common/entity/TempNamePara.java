package com.infinity.jerry.securitysupport.common.entity;

/**
 * Created by jerry on 2017/11/24.
 */

public class TempNamePara {

    private String name;
    private int count ;
    private int companyCode;

    @Override
    public String toString() {
        return "TempNamePara{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", companyCode=" + companyCode +
                '}';
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
