package com.springboot.reditclone.demo.model;

public enum Permissions {

    READ("read"),
    WRITE("write");

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
