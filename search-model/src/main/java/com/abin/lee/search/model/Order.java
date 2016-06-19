package com.abin.lee.search.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-3-27
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    private Integer id;
    private String orderName;
    private String desc;
    private Date createTime;

    public Order(Integer id, String orderName, String desc, Date createTime) {
        this.id = id;
        this.orderName = orderName;
        this.desc = desc;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
