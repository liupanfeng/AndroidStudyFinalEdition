package com.example.designpattern.factory_abstract;

/**
 * 具体的部门表格
 */
public class Department {

    private int _id;

    private String name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
