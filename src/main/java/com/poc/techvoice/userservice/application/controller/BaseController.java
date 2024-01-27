package com.poc.techvoice.userservice.application.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BaseController {

    @Value("${audit.user}")
    private String userIdKey;

    public void setCurrentUser(HttpServletRequest request) {

        String currentUser = request.getHeader(userIdKey);
        if (Objects.nonNull(currentUser)) {
            MDC.put(userIdKey, currentUser);
        }
    }
}
