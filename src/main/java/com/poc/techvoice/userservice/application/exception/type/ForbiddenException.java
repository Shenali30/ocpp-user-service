package com.poc.techvoice.userservice.application.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, String code) {
        super(message, code);
    }

    public ForbiddenException(String message, String code, String displayMsg) {
        super(message, code, displayMsg);
    }
}
