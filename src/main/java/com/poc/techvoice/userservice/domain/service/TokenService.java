package com.poc.techvoice.userservice.domain.service;

import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.TokenDto;

public interface TokenService {

    TokenDto generateUserTokens(User user) throws ServerException;

    TokenDto populateTokenValues(User user, boolean isNewLogin, String refreshToken);
}
