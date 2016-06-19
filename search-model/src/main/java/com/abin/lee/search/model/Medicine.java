package com.abin.lee.search.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 15-12-30
 * Time: 下午11:19
 * To change this template use File | Settings | File Templates.
 */
public class Medicine implements Serializable{

    private Integer id;
    private String name;
    private String function;

    public Medicine() {
        super();
    }

    public Medicine(Integer id, String name, String function) {
        super();
        this.id = id;
        this.name = name;
        this.function = function;
    }

    //getter and  setter ()

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
