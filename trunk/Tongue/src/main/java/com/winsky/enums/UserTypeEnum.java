package com.winsky.enums;

/**
 * author: winsky
 * date: 2017/2/22
 * description:
 */
public enum UserTypeEnum {
    DOCTOR(0, "医生"), PATIENT(1, "患者");

    private int value;
    private String type;

    private UserTypeEnum(int value, String type) {
        this.type = type;
        this.value = value;
    }

    public static UserTypeEnum fromValue(int value) {
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;
    }

    public static UserTypeEnum fromType(String type) {
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return null;
    }

    public int value() {
        return value;
    }

    public String type() {
        return type;
    }
}
