package com.infinity.jerry.securitysupport.common.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jerry on 2017/11/15.
 */

public class CoalCheckItem extends DataSupport implements Serializable{

    @Override
    public String toString() {
        return "CoalCheckItem{" +
                "id=" + id +
                ", comType=" + comType +
                ", comName='" + comName + '\'' +
                ", checkinType=" + checkinType +
                ", checkinName='" + checkinName + '\'' +
                ", checkinSuper='" + checkinSuper + '\'' +
                ", checkinItem='" + checkinItem + '\'' +
                ", parent='" + parent + '\'' +
                ", checkinContent='" + checkinContent + '\'' +
                ", checkinRequest='" + checkinRequest + '\'' +
                ", checkinReference='" + checkinReference + '\'' +
                ", c12='" + c12 + '\'' +
                ", c13='" + c13 + '\'' +
                ", c14='" + c14 + '\'' +
                ", c15='" + c15 + '\'' +
                '}';
    }

    private int id;
    private int comType;
    private String comName;
    private int checkinType;
    private String checkinName;
    private String checkinSuper;
    private String checkinItem;
    private String parent;
    private String checkinContent;
    private String checkinRequest;
    private String checkinReference;
    private String c12;
    private String c13;
    private String c14;
    private String c15;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComType() {
        return comType;
    }

    public void setComType(int comType) {
        this.comType = comType;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public int getCheckinType() {
        return checkinType;
    }

    public void setCheckinType(int checkinType) {
        this.checkinType = checkinType;
    }

    public String getCheckinName() {
        return checkinName;
    }

    public void setCheckinName(String checkinName) {
        this.checkinName = checkinName;
    }

    public String getCheckinSuper() {
        return checkinSuper;
    }

    public void setCheckinSuper(String checkinSuper) {
        this.checkinSuper = checkinSuper;
    }

    public String getCheckinItem() {
        return checkinItem;
    }

    public void setCheckinItem(String checkinItem) {
        this.checkinItem = checkinItem;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCheckinContent() {
        return checkinContent;
    }

    public void setCheckinContent(String checkinContent) {
        this.checkinContent = checkinContent;
    }

    public String getCheckinRequest() {
        return checkinRequest;
    }

    public void setCheckinRequest(String checkinRequest) {
        this.checkinRequest = checkinRequest;
    }

    public String getCheckinReference() {
        return checkinReference;
    }

    public void setCheckinReference(String checkinReference) {
        this.checkinReference = checkinReference;
    }

    public String getC12() {
        return c12;
    }

    public void setC12(String c12) {
        this.c12 = c12;
    }

    public String getC13() {
        return c13;
    }

    public void setC13(String c13) {
        this.c13 = c13;
    }

    public String getC14() {
        return c14;
    }

    public void setC14(String c14) {
        this.c14 = c14;
    }

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }
}
