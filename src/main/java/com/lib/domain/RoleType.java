package com.lib.domain;

public enum RoleType {

    ROLE_MEMBER("Member"),
    ROLE_EMPLOYEE("Employee"),
    ROLE_ADMIN("Administrator");

    private final String name;

    private RoleType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
