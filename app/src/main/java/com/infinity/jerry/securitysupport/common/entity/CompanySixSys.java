package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanySixSys extends DataSupport {

    @Override
    public String toString() {
        return "CompanySixSys{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", monitor='" + monitor + '\'' +
                ", monitorFinish='" + monitorFinish + '\'' +
                ", windSave='" + windSave + '\'' +
                ", windSaveFinish='" + windSaveFinish + '\'' +
                ", waterSave='" + waterSave + '\'' +
                ", waterSaveFinish='" + waterSaveFinish + '\'' +
                ", netSys='" + netSys + '\'' +
                ", netSysFinish='" + netSysFinish + '\'' +
                ", underSave='" + underSave + '\'' +
                ", underSaveFinish='" + underSaveFinish + '\'' +
                ", underLocation='" + underLocation + '\'' +
                ", underLocationFinish='" + underLocationFinish + '\'' +
                '}';
    }

    private int id;
    private int companyCode;
    private String monitor;
    private String monitorFinish;
    private String windSave;
    private String windSaveFinish;
    private String waterSave;
    private String waterSaveFinish;
    private String netSys;
    private String netSysFinish;
    private String underSave;
    private String underSaveFinish;
    private String underLocation;
    private String underLocationFinish;

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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getMonitorFinish() {
        return monitorFinish;
    }

    public void setMonitorFinish(String monitorFinish) {
        this.monitorFinish = monitorFinish;
    }

    public String getWindSave() {
        return windSave;
    }

    public void setWindSave(String windSave) {
        this.windSave = windSave;
    }

    public String getWindSaveFinish() {
        return windSaveFinish;
    }

    public void setWindSaveFinish(String windSaveFinish) {
        this.windSaveFinish = windSaveFinish;
    }

    public String getWaterSave() {
        return waterSave;
    }

    public void setWaterSave(String waterSave) {
        this.waterSave = waterSave;
    }

    public String getWaterSaveFinish() {
        return waterSaveFinish;
    }

    public void setWaterSaveFinish(String waterSaveFinish) {
        this.waterSaveFinish = waterSaveFinish;
    }

    public String getNetSys() {
        return netSys;
    }

    public void setNetSys(String netSys) {
        this.netSys = netSys;
    }

    public String getNetSysFinish() {
        return netSysFinish;
    }

    public void setNetSysFinish(String netSysFinish) {
        this.netSysFinish = netSysFinish;
    }

    public String getUnderSave() {
        return underSave;
    }

    public void setUnderSave(String underSave) {
        this.underSave = underSave;
    }

    public String getUnderSaveFinish() {
        return underSaveFinish;
    }

    public void setUnderSaveFinish(String underSaveFinish) {
        this.underSaveFinish = underSaveFinish;
    }

    public String getUnderLocation() {
        return underLocation;
    }

    public void setUnderLocation(String underLocation) {
        this.underLocation = underLocation;
    }

    public String getUnderLocationFinish() {
        return underLocationFinish;
    }

    public void setUnderLocationFinish(String underLocationFinish) {
        this.underLocationFinish = underLocationFinish;
    }
}
