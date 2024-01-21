package com.poc.techvoice.userservice.application.enums;

public enum ResponseEnum {

    SUCCESS("00", "Success", "Success"),
    REQUEST_VALIDATION_ERROR("01", "Bad Request", "Incorrect Input"),
    USER_ALREADY_EXISTS("02", "User already exists", "This user already has an existing account"),
    INTERNAL_ERROR("99", "Internal Error", "An Error Occurred. Please Contact Administrator");
    private final String code;
    private final String desc;
    private final String displayDesc;

    ResponseEnum(String code, String desc, String displayDesc) {
        this.code = code;
        this.desc = desc;
        this.displayDesc = displayDesc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getDisplayDesc() {
        return displayDesc;
    }
}
