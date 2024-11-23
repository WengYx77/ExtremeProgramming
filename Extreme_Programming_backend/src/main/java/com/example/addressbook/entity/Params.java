package com.example.addressbook.entity;

public class Params {
    private String name;
    private String phone;
    private String phone2;
    private Integer pageNum;
    private Integer pageSize;
    private Integer collect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() { return phone2; }

    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCollect() { return collect; }

    public void setCollect(Integer collect) { this.collect = collect; }
}
