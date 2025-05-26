package com.compass.ecommercechallenge;

public enum UserRoleEnum {
    ADMIN(0),
    CLIENT(1);

    long roleId;

    UserRoleEnum(long roleId) {
        this.roleId = roleId;
    }
}
