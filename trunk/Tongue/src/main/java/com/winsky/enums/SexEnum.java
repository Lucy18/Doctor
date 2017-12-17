package com.winsky.enums;

/**
 * author: winsky
 * date: 2017/2/22
 * description:
 */
public enum SexEnum {
    WOMAN(0, "女"), MAN(1, "男");

    private int value;
    private String type;

    private SexEnum(int value, String type) {
        this.type = type;
        this.value = value;
    }

    public static SexEnum fromValue(int value) {
        for (SexEnum e : SexEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        return null;
    }

    public static SexEnum fromType(String type) {
        for (SexEnum e : SexEnum.values()) {
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
