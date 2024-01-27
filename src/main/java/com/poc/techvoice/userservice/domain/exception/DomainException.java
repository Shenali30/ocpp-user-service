package com.poc.techvoice.userservice.domain.exception;

import com.poc.techvoice.userservice.application.exception.type.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends BaseException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, String code) {
        super(message, code);
    }

    public DomainException(String message, String code, String displayMsg) {
        super(message, code, displayMsg);
    }
}
