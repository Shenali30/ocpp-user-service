package com.poc.techvoice.userservice.application.controller;

import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.exception.type.ForbiddenException;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLoginRequest;
import com.poc.techvoice.userservice.application.validator.RequestEntityValidator;
import com.poc.techvoice.userservice.domain.entities.dto.response.UserTokenResponse;
import com.poc.techvoice.userservice.domain.exception.DomainException;
import com.poc.techvoice.userservice.domain.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("${base-url.context}/user")
public class AuthController extends BaseController {

    private final RequestEntityValidator requestValidator;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> loginUser(@RequestHeader("user-id") String userId,
                                                       @RequestBody UserLoginRequest userLoginRequest,
                                                       HttpServletRequest request) throws ServerException, ForbiddenException, DomainException {

        log.info(LoggingConstants.USER_LOGIN_REQUEST_INITIATED);
        setCurrentUser(request);

        requestValidator.validate(userLoginRequest);
        UserTokenResponse response = authService.loginUser(userLoginRequest);

        log.info(LoggingConstants.USER_LOGIN_RESPONSE_SENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
