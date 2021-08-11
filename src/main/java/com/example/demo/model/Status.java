package com.example.demo.model;

public enum Status {
    Approved("Approved"),
    Declined("Declined"),
    OnHold("On Hold");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
