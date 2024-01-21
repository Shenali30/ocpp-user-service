package com.poc.techvoice.userservice.domain.service;

import com.poc.techvoice.userservice.application.exception.type.ForbiddenException;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLoginRequest;
import com.poc.techvoice.userservice.domain.entities.dto.response.UserTokenResponse;
import com.poc.techvoice.userservice.domain.exception.DomainException;

public interface AuthService {

    UserTokenResponse loginUser(UserLoginRequest userLoginRequest) throws ServerException, ForbiddenException, DomainException;
}
