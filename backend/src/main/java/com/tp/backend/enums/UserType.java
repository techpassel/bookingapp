package com.tp.backend.enums;

public enum UserType {
    User("user"),
    Admin("admin");

    public String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
