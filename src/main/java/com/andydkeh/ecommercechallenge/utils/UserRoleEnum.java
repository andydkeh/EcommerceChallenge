package com.andydkeh.ecommercechallenge.utils;

public enum UserRoleEnum {
    ADMIN(0),
    CLIENT(1);

    long roleId;

    UserRoleEnum(long roleId) {
        this.roleId = roleId;
    }
}
