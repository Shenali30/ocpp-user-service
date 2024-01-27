package com.poc.techvoice.userservice.domain.service;

import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.enums.TokenType;

import java.util.Map;

public interface JwtService {

    String generateToken(User user, TokenType tokenType);

    Map<String, Object> getAllClaimsFromTokenAsMap(String token);

    boolean isTokenExpired(String authToken);
}
