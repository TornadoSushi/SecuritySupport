package com.infinity.jerry.securitysupport.common.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class CompanyCoalEntityNew {

    private int code;
    private String msg;
    private boolean flag;
    private Object token;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * coal_cell : 58410124
         * coal_phone : 58410124
         * coal_url : http://202.98.60.89:3301/cqmt/users/mkxxxg/TestFirst.jsp?regionid=1&mid=65
         * company_area : 万州区
         * company_code : 3514
         * company_location : 渝东监察分局
         * company_name : 万州区刘家沟煤矿
         * company_state : 改扩建
         * director : 谢万贵
         * leagl_person : 高金贤
         */

        private int id;
        private String coal_cell;
        private String coal_phone;
        private String coal_url;
        private String company_area;
        private int company_code;
        private String company_location;
        private String company_name;
        private String company_state;
        private String director;
        private String leagl_person;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoal_cell() {
            return coal_cell;
        }

        public void setCoal_cell(String coal_cell) {
            this.coal_cell = coal_cell;
        }

        public String getCoal_phone() {
            return coal_phone;
        }

        public void setCoal_phone(String coal_phone) {
            this.coal_phone = coal_phone;
        }

        public String getCoal_url() {
            return coal_url;
        }

        public void setCoal_url(String coal_url) {
            this.coal_url = coal_url;
        }

        public String getCompany_area() {
            return company_area;
        }

        public void setCompany_area(String company_area) {
            this.company_area = company_area;
        }

        public int getCompany_code() {
            return company_code;
        }

        public void setCompany_code(int company_code) {
            this.company_code = company_code;
        }

        public String getCompany_location() {
            return company_location;
        }

        public void setCompany_location(String company_location) {
            this.company_location = company_location;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_state() {
            return company_state;
        }

        public void setCompany_state(String company_state) {
            this.company_state = company_state;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getLeagl_person() {
            return leagl_person;
        }

        public void setLeagl_person(String leagl_person) {
            this.leagl_person = leagl_person;
        }
    }
}
