package com.company.model;

import com.company.constant.Constant;

public class SubjectClass implements ShowDetails {
    private String id;
    private String name;

    public SubjectClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + Constant.SEPARATE + name;
    }

    @Override
    public String showDetails() {
        String data = String.format("%-20s %-20s", id,name);
        return data;
    }
}
