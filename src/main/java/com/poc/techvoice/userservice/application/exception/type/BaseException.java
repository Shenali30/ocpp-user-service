package com.poc.techvoice.userservice.application.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BaseException extends Exception {

    private final String code;
    private final String displayMsg;

    public BaseException(String message) {
        super(message);
        this.code = null;
        this.displayMsg = null;
    }

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
        this.displayMsg = null;
    }

    public BaseException(String message, String code, String displayMsg) {
        super(message);
        this.code = code;
        this.displayMsg = displayMsg;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayMsg() {
        return displayMsg;
    }
}
