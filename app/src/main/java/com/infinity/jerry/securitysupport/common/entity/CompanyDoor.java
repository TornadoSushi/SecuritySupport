package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanyDoor extends DataSupport {

    @Override
    public String toString() {
        return "CompanyDoor{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", doorName='" + doorName + '\'' +
                ", axisX='" + axisX + '\'' +
                ", axisY='" + axisY + '\'' +
                ", axisZ='" + axisZ + '\'' +
                '}';
    }



    private int id;
    private int companyCode;
    private String doorName;
    private String axisX;
    private String axisY;
    private String axisZ;

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

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getAxisX() {
        return axisX;
    }

    public void setAxisX(String axisX) {
        this.axisX = axisX;
    }

    public String getAxisY() {
        return axisY;
    }

    public void setAxisY(String axisY) {
        this.axisY = axisY;
    }

    public String getAxisZ() {
        return axisZ;
    }

    public void setAxisZ(String axisZ) {
        this.axisZ = axisZ;
    }
}
