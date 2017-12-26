package com.infinity.jerry.securitysupport.common.test;

import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;

import java.util.List;

/**
 * Created by jerry on 2017/11/14.
 */

public class TestCompany {

    private boolean last;
    private int totalElements;
    private int totalPages;
    private int size;
    private int number;
    private Object sort;
    private int numberOfElements;
    private boolean first;
    private List<CompanyCoalEntity> content;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public List<CompanyCoalEntity> getContent() {
        return content;
    }

    public void setContent(List<CompanyCoalEntity> content) {
        this.content = content;
    }


}
