package com.project.finalproject;

public class MyImage {
    private String name;
    private String id;

    public MyImage() {
        name = "";
        id = "";
    }

    public MyImage(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
