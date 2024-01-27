package com.poc.techvoice.userservice.domain.service;

import com.poc.techvoice.userservice.application.exception.type.ForbiddenException;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.exception.type.UserValidationException;
import com.poc.techvoice.userservice.application.transport.request.entities.RefreshTokenRequest;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLoginRequest;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLogoutRequest;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.entities.dto.response.UserTokenResponse;
import com.poc.techvoice.userservice.domain.enums.TokenType;
import com.poc.techvoice.userservice.domain.exception.DomainException;

import java.util.Map;

public interface AuthService {

    UserTokenResponse loginUser(UserLoginRequest userLoginRequest) throws ServerException, ForbiddenException, DomainException;

    BaseResponse logoutUser(UserLogoutRequest userLogoutRequest) throws ServerException, DomainException;

    UserTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ServerException, UserValidationException;

    boolean isTokenValid(String token, TokenType tokenType, Map<String, User> tokenUserData) throws ServerException;
}
