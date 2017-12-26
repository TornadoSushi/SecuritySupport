package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanySafty extends DataSupport {

    private int id;
    private int companyCode;
    private String gasPump;
    private String gasMonitor;
    private String gasIsNet;
    private String manLocation;
    private String mainFanNum;
    private String mainFanElcNum;
    private String mainFanRatePower;
    private String subFanNum;
    private String subFanElcNum;
    private String subFanRatePower;
    private String ratedBlast;
    private String ratedPressure;
    private String dowMethod;
    private String dowNum;
    private String dowCount;
    private String dowPower;
    private String dowMax;
    private String dowRadius;
    private String hasGasPump;
    private String gasPumpMethod;
    private String pumpNum;
    private String pumpSize;
    private String hasTube;
    private String tubeNum;
    private String hasN2;
    private String n2Type;
    private String n2Num;
    private String hasInjection;
    private String injectionType;
    private String injectionNum;
    private String injectionForday;
    private String otherFireSys;
    private String hasMonitor;
    private String monitorNum;
    private String monitorCount;
    private String gasSensorCount;
    private String coSensorCount;
    private String windSensorCount;
    private String tempSensorCount;
    private String onoffSensorCount;
    private String otherSensorCount;
    private String ssProducePlace;
    private String ssProduceTime;
    private String ssInstallTime;
    private String underElcSys;
    private String underElcMethod;
    private String transformerPower;
    private String mainLoop;
    private String mainLoopType;
    private String subLoop;
    private String subLoopType;
    private String undercoalElcSys;
    private String intoMainPressure;
    private String intoMainCable;
    private String windSys;
    private String windSysType;

    @Override
    public String toString() {
        return "CompanySafty{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", gasPump='" + gasPump + '\'' +
                ", gasMonitor='" + gasMonitor + '\'' +
                ", gasIsNet='" + gasIsNet + '\'' +
                ", manLocation='" + manLocation + '\'' +
                ", mainFanNum='" + mainFanNum + '\'' +
                ", mainFanElcNum='" + mainFanElcNum + '\'' +
                ", mainFanRatePower='" + mainFanRatePower + '\'' +
                ", subFanNum='" + subFanNum + '\'' +
                ", subFanElcNum='" + subFanElcNum + '\'' +
                ", subFanRatePower='" + subFanRatePower + '\'' +
                ", ratedBlast='" + ratedBlast + '\'' +
                ", ratedPressure='" + ratedPressure + '\'' +
                ", dowMethod='" + dowMethod + '\'' +
                ", dowNum='" + dowNum + '\'' +
                ", dowCount='" + dowCount + '\'' +
                ", dowPower='" + dowPower + '\'' +
                ", dowMax='" + dowMax + '\'' +
                ", dowRadius='" + dowRadius + '\'' +
                ", hasGasPump='" + hasGasPump + '\'' +
                ", gasPumpMethod='" + gasPumpMethod + '\'' +
                ", pumpNum='" + pumpNum + '\'' +
                ", pumpSize='" + pumpSize + '\'' +
                ", hasTube='" + hasTube + '\'' +
                ", tubeNum='" + tubeNum + '\'' +
                ", hasN2='" + hasN2 + '\'' +
                ", n2Type='" + n2Type + '\'' +
                ", n2Num='" + n2Num + '\'' +
                ", hasInjection='" + hasInjection + '\'' +
                ", injectionType='" + injectionType + '\'' +
                ", injectionNum='" + injectionNum + '\'' +
                ", injectionForday='" + injectionForday + '\'' +
                ", otherFireSys='" + otherFireSys + '\'' +
                ", hasMonitor='" + hasMonitor + '\'' +
                ", monitorNum='" + monitorNum + '\'' +
                ", monitorCount='" + monitorCount + '\'' +
                ", gasSensorCount='" + gasSensorCount + '\'' +
                ", coSensorCount='" + coSensorCount + '\'' +
                ", windSensorCount='" + windSensorCount + '\'' +
                ", tempSensorCount='" + tempSensorCount + '\'' +
                ", onoffSensorCount='" + onoffSensorCount + '\'' +
                ", otherSensorCount='" + otherSensorCount + '\'' +
                ", ssProducePlace='" + ssProducePlace + '\'' +
                ", ssProduceTime='" + ssProduceTime + '\'' +
                ", ssInstallTime='" + ssInstallTime + '\'' +
                ", underElcSys='" + underElcSys + '\'' +
                ", underElcMethod='" + underElcMethod + '\'' +
                ", transformerPower='" + transformerPower + '\'' +
                ", mainLoop='" + mainLoop + '\'' +
                ", mainLoopType='" + mainLoopType + '\'' +
                ", subLoop='" + subLoop + '\'' +
                ", subLoopType='" + subLoopType + '\'' +
                ", undercoalElcSys='" + undercoalElcSys + '\'' +
                ", intoMainPressure='" + intoMainPressure + '\'' +
                ", intoMainCable='" + intoMainCable + '\'' +
                ", windSys='" + windSys + '\'' +
                ", windSysType='" + windSysType + '\'' +
                '}';
    }

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

    public String getGasPump() {
        return gasPump;
    }

    public void setGasPump(String gasPump) {
        this.gasPump = gasPump;
    }

    public String getGasMonitor() {
        return gasMonitor;
    }

    public void setGasMonitor(String gasMonitor) {
        this.gasMonitor = gasMonitor;
    }

    public String getGasIsNet() {
        return gasIsNet;
    }

    public void setGasIsNet(String gasIsNet) {
        this.gasIsNet = gasIsNet;
    }

    public String getManLocation() {
        return manLocation;
    }

    public void setManLocation(String manLocation) {
        this.manLocation = manLocation;
    }

    public String getMainFanNum() {
        return mainFanNum;
    }

    public void setMainFanNum(String mainFanNum) {
        this.mainFanNum = mainFanNum;
    }

    public String getMainFanElcNum() {
        return mainFanElcNum;
    }

    public void setMainFanElcNum(String mainFanElcNum) {
        this.mainFanElcNum = mainFanElcNum;
    }

    public String getMainFanRatePower() {
        return mainFanRatePower;
    }

    public void setMainFanRatePower(String mainFanRatePower) {
        this.mainFanRatePower = mainFanRatePower;
    }

    public String getSubFanNum() {
        return subFanNum;
    }

    public void setSubFanNum(String subFanNum) {
        this.subFanNum = subFanNum;
    }

    public String getSubFanElcNum() {
        return subFanElcNum;
    }

    public void setSubFanElcNum(String subFanElcNum) {
        this.subFanElcNum = subFanElcNum;
    }

    public String getSubFanRatePower() {
        return subFanRatePower;
    }

    public void setSubFanRatePower(String subFanRatePower) {
        this.subFanRatePower = subFanRatePower;
    }

    public String getRatedBlast() {
        return ratedBlast;
    }

    public void setRatedBlast(String ratedBlast) {
        this.ratedBlast = ratedBlast;
    }

    public String getRatedPressure() {
        return ratedPressure;
    }

    public void setRatedPressure(String ratedPressure) {
        this.ratedPressure = ratedPressure;
    }

    public String getDowMethod() {
        return dowMethod;
    }

    public void setDowMethod(String dowMethod) {
        this.dowMethod = dowMethod;
    }

    public String getDowNum() {
        return dowNum;
    }

    public void setDowNum(String dowNum) {
        this.dowNum = dowNum;
    }

    public String getDowCount() {
        return dowCount;
    }

    public void setDowCount(String dowCount) {
        this.dowCount = dowCount;
    }

    public String getDowPower() {
        return dowPower;
    }

    public void setDowPower(String dowPower) {
        this.dowPower = dowPower;
    }

    public String getDowMax() {
        return dowMax;
    }

    public void setDowMax(String dowMax) {
        this.dowMax = dowMax;
    }

    public String getDowRadius() {
        return dowRadius;
    }

    public void setDowRadius(String dowRadius) {
        this.dowRadius = dowRadius;
    }

    public String getHasGasPump() {
        return hasGasPump;
    }

    public void setHasGasPump(String hasGasPump) {
        this.hasGasPump = hasGasPump;
    }

    public String getGasPumpMethod() {
        return gasPumpMethod;
    }

    public void setGasPumpMethod(String gasPumpMethod) {
        this.gasPumpMethod = gasPumpMethod;
    }

    public String getPumpNum() {
        return pumpNum;
    }

    public void setPumpNum(String pumpNum) {
        this.pumpNum = pumpNum;
    }

    public String getPumpSize() {
        return pumpSize;
    }

    public void setPumpSize(String pumpSize) {
        this.pumpSize = pumpSize;
    }

    public String getHasTube() {
        return hasTube;
    }

    public void setHasTube(String hasTube) {
        this.hasTube = hasTube;
    }

    public String getTubeNum() {
        return tubeNum;
    }

    public void setTubeNum(String tubeNum) {
        this.tubeNum = tubeNum;
    }

    public String getHasN2() {
        return hasN2;
    }

    public void setHasN2(String hasN2) {
        this.hasN2 = hasN2;
    }

    public String getN2Type() {
        return n2Type;
    }

    public void setN2Type(String n2Type) {
        this.n2Type = n2Type;
    }

    public String getN2Num() {
        return n2Num;
    }

    public void setN2Num(String n2Num) {
        this.n2Num = n2Num;
    }

    public String getHasInjection() {
        return hasInjection;
    }

    public void setHasInjection(String hasInjection) {
        this.hasInjection = hasInjection;
    }

    public String getInjectionType() {
        return injectionType;
    }

    public void setInjectionType(String injectionType) {
        this.injectionType = injectionType;
    }

    public String getInjectionNum() {
        return injectionNum;
    }

    public void setInjectionNum(String injectionNum) {
        this.injectionNum = injectionNum;
    }

    public String getInjectionForday() {
        return injectionForday;
    }

    public void setInjectionForday(String injectionForday) {
        this.injectionForday = injectionForday;
    }

    public String getOtherFireSys() {
        return otherFireSys;
    }

    public void setOtherFireSys(String otherFireSys) {
        this.otherFireSys = otherFireSys;
    }

    public String getHasMonitor() {
        return hasMonitor;
    }

    public void setHasMonitor(String hasMonitor) {
        this.hasMonitor = hasMonitor;
    }

    public String getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(String monitorNum) {
        this.monitorNum = monitorNum;
    }

    public String getMonitorCount() {
        return monitorCount;
    }

    public void setMonitorCount(String monitorCount) {
        this.monitorCount = monitorCount;
    }

    public String getGasSensorCount() {
        return gasSensorCount;
    }

    public void setGasSensorCount(String gasSensorCount) {
        this.gasSensorCount = gasSensorCount;
    }

    public String getCoSensorCount() {
        return coSensorCount;
    }

    public void setCoSensorCount(String coSensorCount) {
        this.coSensorCount = coSensorCount;
    }

    public String getWindSensorCount() {
        return windSensorCount;
    }

    public void setWindSensorCount(String windSensorCount) {
        this.windSensorCount = windSensorCount;
    }

    public String getTempSensorCount() {
        return tempSensorCount;
    }

    public void setTempSensorCount(String tempSensorCount) {
        this.tempSensorCount = tempSensorCount;
    }

    public String getOnoffSensorCount() {
        return onoffSensorCount;
    }

    public void setOnoffSensorCount(String onoffSensorCount) {
        this.onoffSensorCount = onoffSensorCount;
    }

    public String getOtherSensorCount() {
        return otherSensorCount;
    }

    public void setOtherSensorCount(String otherSensorCount) {
        this.otherSensorCount = otherSensorCount;
    }

    public String getSsProducePlace() {
        return ssProducePlace;
    }

    public void setSsProducePlace(String ssProducePlace) {
        this.ssProducePlace = ssProducePlace;
    }

    public String getSsProduceTime() {
        return ssProduceTime;
    }

    public void setSsProduceTime(String ssProduceTime) {
        this.ssProduceTime = ssProduceTime;
    }

    public String getSsInstallTime() {
        return ssInstallTime;
    }

    public void setSsInstallTime(String ssInstallTime) {
        this.ssInstallTime = ssInstallTime;
    }

    public String getUnderElcSys() {
        return underElcSys;
    }

    public void setUnderElcSys(String underElcSys) {
        this.underElcSys = underElcSys;
    }

    public String getUnderElcMethod() {
        return underElcMethod;
    }

    public void setUnderElcMethod(String underElcMethod) {
        this.underElcMethod = underElcMethod;
    }

    public String getTransformerPower() {
        return transformerPower;
    }

    public void setTransformerPower(String transformerPower) {
        this.transformerPower = transformerPower;
    }

    public String getMainLoop() {
        return mainLoop;
    }

    public void setMainLoop(String mainLoop) {
        this.mainLoop = mainLoop;
    }

    public String getMainLoopType() {
        return mainLoopType;
    }

    public void setMainLoopType(String mainLoopType) {
        this.mainLoopType = mainLoopType;
    }

    public String getSubLoop() {
        return subLoop;
    }

    public void setSubLoop(String subLoop) {
        this.subLoop = subLoop;
    }

    public String getSubLoopType() {
        return subLoopType;
    }

    public void setSubLoopType(String subLoopType) {
        this.subLoopType = subLoopType;
    }

    public String getUndercoalElcSys() {
        return undercoalElcSys;
    }

    public void setUndercoalElcSys(String undercoalElcSys) {
        this.undercoalElcSys = undercoalElcSys;
    }

    public String getIntoMainPressure() {
        return intoMainPressure;
    }

    public void setIntoMainPressure(String intoMainPressure) {
        this.intoMainPressure = intoMainPressure;
    }

    public String getIntoMainCable() {
        return intoMainCable;
    }

    public void setIntoMainCable(String intoMainCable) {
        this.intoMainCable = intoMainCable;
    }

    public String getWindSys() {
        return windSys;
    }

    public void setWindSys(String windSys) {
        this.windSys = windSys;
    }

    public String getWindSysType() {
        return windSysType;
    }

    public void setWindSysType(String windSysType) {
        this.windSysType = windSysType;
    }
}
