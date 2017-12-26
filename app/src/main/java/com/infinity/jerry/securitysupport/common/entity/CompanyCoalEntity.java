package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jerry on 2017/11/14.
 */

public class CompanyCoalEntity extends DataSupport {

    private int id;
    private int companyCode;
    private String companyName;
    private String companyLocation;
    private String companyArea;
    private String companyState;
    private String director;
    private String leaglPerson;
    private String coalPhone;
    private String coalCell;
    private String coalUrl;


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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyArea() {
        return companyArea;
    }

    public void setCompanyArea(String companyArea) {
        this.companyArea = companyArea;
    }

    public String getCompanyState() {
        return companyState;
    }

    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLeaglPerson() {
        return leaglPerson;
    }

    public void setLeaglPerson(String leaglPerson) {
        this.leaglPerson = leaglPerson;
    }

    public String getCoalPhone() {
        return coalPhone;
    }

    public void setCoalPhone(String coalPhone) {
        this.coalPhone = coalPhone;
    }

    public String getCoalCell() {
        return coalCell;
    }

    public void setCoalCell(String coalCell) {
        this.coalCell = coalCell;
    }

    public String getCoalUrl() {
        return coalUrl;
    }

    public void setCoalUrl(String coalUrl) {
        this.coalUrl = coalUrl;
    }

}
