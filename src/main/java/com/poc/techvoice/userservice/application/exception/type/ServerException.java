package com.poc.techvoice.userservice.application.exception.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends BaseException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, String code) {
        super(message, code);
    }

    public ServerException(String message, String code, String displayMsg) {
        super(message, code, displayMsg);
    }
}
