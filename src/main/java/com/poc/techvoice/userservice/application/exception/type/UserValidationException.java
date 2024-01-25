package com.poc.techvoice.userservice.application.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserValidationException extends BaseException {

    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(String message, String code) {
        super(message, code);
    }

    public UserValidationException(String message, String code, String displayMsg) {
        super(message, code, displayMsg);
    }
}
