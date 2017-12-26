package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanyProduct extends DataSupport {

    @Override
    public String toString() {
        return "CompanyProduct{" +
                "id=" + id +
                ", companyCode=" + companyCode +
                ", coalUp='" + coalUp + '\'' +
                ", transMethod='" + transMethod + '\'' +
                ", coalTech='" + coalTech + '\'' +
                ", coalBackRate='" + coalBackRate + '\'' +
                ", coalStartTime='" + coalStartTime + '\'' +
                ", upTransSys='" + upTransSys + '\'' +
                ", upTransMethod='" + upTransMethod + '\'' +
                ", transEquipNum='" + transEquipNum + '\'' +
                ", powerSys='" + powerSys + '\'' +
                ", powerMethod='" + powerMethod + '\'' +
                ", transformerPower='" + transformerPower + '\'' +
                ", mainLoop='" + mainLoop + '\'' +
                ", mainLoopType='" + mainLoopType + '\'' +
                ", backupLoop='" + backupLoop + '\'' +
                ", backupLoopType='" + backupLoopType + '\'' +
                ", downPowerSupply='" + downPowerSupply + '\'' +
                ", intoPower='" + intoPower + '\'' +
                ", intoMainPower='" + intoMainPower + '\'' +
                '}';
    }


    private int id;
    private int companyCode;
    private String coalUp;
    private String transMethod;
    private String coalTech;
    private String coalBackRate;
    private String coalStartTime;
    private String upTransSys;
    private String upTransMethod;
    private String transEquipNum;
    private String powerSys;
    private String powerMethod;
    private String transformerPower;
    private String mainLoop;
    private String mainLoopType;
    private String backupLoop;
    private String backupLoopType;
    private String downPowerSupply;
    private String intoPower;
    private String intoMainPower;

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

    public String getCoalUp() {
        return coalUp;
    }

    public void setCoalUp(String coalUp) {
        this.coalUp = coalUp;
    }

    public String getTransMethod() {
        return transMethod;
    }

    public void setTransMethod(String transMethod) {
        this.transMethod = transMethod;
    }

    public String getCoalTech() {
        return coalTech;
    }

    public void setCoalTech(String coalTech) {
        this.coalTech = coalTech;
    }

    public String getCoalBackRate() {
        return coalBackRate;
    }

    public void setCoalBackRate(String coalBackRate) {
        this.coalBackRate = coalBackRate;
    }

    public String getCoalStartTime() {
        return coalStartTime;
    }

    public void setCoalStartTime(String coalStartTime) {
        this.coalStartTime = coalStartTime;
    }

    public String getUpTransSys() {
        return upTransSys;
    }

    public void setUpTransSys(String upTransSys) {
        this.upTransSys = upTransSys;
    }

    public String getUpTransMethod() {
        return upTransMethod;
    }

    public void setUpTransMethod(String upTransMethod) {
        this.upTransMethod = upTransMethod;
    }

    public String getTransEquipNum() {
        return transEquipNum;
    }

    public void setTransEquipNum(String transEquipNum) {
        this.transEquipNum = transEquipNum;
    }

    public String getPowerSys() {
        return powerSys;
    }

    public void setPowerSys(String powerSys) {
        this.powerSys = powerSys;
    }

    public String getPowerMethod() {
        return powerMethod;
    }

    public void setPowerMethod(String powerMethod) {
        this.powerMethod = powerMethod;
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

    public String getBackupLoop() {
        return backupLoop;
    }

    public void setBackupLoop(String backupLoop) {
        this.backupLoop = backupLoop;
    }

    public String getBackupLoopType() {
        return backupLoopType;
    }

    public void setBackupLoopType(String backupLoopType) {
        this.backupLoopType = backupLoopType;
    }

    public String getDownPowerSupply() {
        return downPowerSupply;
    }

    public void setDownPowerSupply(String downPowerSupply) {
        this.downPowerSupply = downPowerSupply;
    }

    public String getIntoPower() {
        return intoPower;
    }

    public void setIntoPower(String intoPower) {
        this.intoPower = intoPower;
    }

    public String getIntoMainPower() {
        return intoMainPower;
    }

    public void setIntoMainPower(String intoMainPower) {
        this.intoMainPower = intoMainPower;
    }
}
