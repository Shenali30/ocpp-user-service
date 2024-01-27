package com.poc.techvoice.userservice.application.controller;

import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserCreateRequest;
import com.poc.techvoice.userservice.application.validator.RequestEntityValidator;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.exception.DomainException;
import com.poc.techvoice.userservice.domain.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "User Account Controller")
@RequestMapping("${base-url.context}/users")
public class UserAccountController {

    private final RequestEntityValidator validator;
    private final UserAccountService userAccountService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) throws DomainException, ServerException {

        log.info(LoggingConstants.USER_CREATE_REQUEST_INITIATED);
        log.debug(LoggingConstants.FULL_REQUEST, userCreateRequest.toString());

        validator.validate(userCreateRequest);
        BaseResponse response = userAccountService.createNewUser(userCreateRequest);

        log.info(LoggingConstants.USER_CREATE_RESPONSE_SENT);
        log.debug(LoggingConstants.FULL_RESPONSE, response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
