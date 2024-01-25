package com.poc.techvoice.userservice.domain.service.impl;

import com.poc.techvoice.userservice.application.constants.AppConstants;
import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.application.exception.type.ForbiddenException;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.exception.type.UserValidationException;
import com.poc.techvoice.userservice.application.transport.request.entities.RefreshTokenRequest;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLoginRequest;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.TokenDto;
import com.poc.techvoice.userservice.domain.entities.dto.UserDetails;
import com.poc.techvoice.userservice.domain.entities.dto.response.UserTokenResponse;
import com.poc.techvoice.userservice.domain.enums.TokenType;
import com.poc.techvoice.userservice.domain.exception.DomainException;
import com.poc.techvoice.userservice.domain.service.AuthService;
import com.poc.techvoice.userservice.domain.service.JwtService;
import com.poc.techvoice.userservice.domain.service.TokenService;
import com.poc.techvoice.userservice.domain.util.PasswordService;
import com.poc.techvoice.userservice.domain.util.UtilityService;
import com.poc.techvoice.userservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl extends UtilityService implements AuthService {

    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public UserTokenResponse loginUser(UserLoginRequest request) throws ServerException, ForbiddenException, DomainException {

        try {
            log.debug(LoggingConstants.USER_LOGIN_LOG, "Login new user: " + request.getEmail(), LoggingConstants.STARTED);
            User user = userRepository.findByEmail(request.getEmail());

            if (Objects.nonNull(user)) {
                validateEnteredPassword(request.getPassword(), user.getPassword());

                log.debug(LoggingConstants.USER_LOGIN_LOG, "Generating tokens", null);
                TokenDto tokenDto = tokenService.generateUserTokens(user);
                UserDetails userDetails = getUserDetails(user);

                log.debug(LoggingConstants.USER_LOGIN_LOG, "Login new user: " + request.getEmail(), LoggingConstants.ENDED);
                return UserTokenResponse.builder()
                        .userDetails(userDetails)
                        .tokenDto(tokenDto)
                        .baseResponse(getSuccessBaseResponse("User Logged In Successfully"))
                        .build();

            } else {
                log.error(LoggingConstants.USER_LOGIN_ERROR, ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getDesc(), null);
                throw new DomainException(ResponseEnum.INVALID_USER.getDesc(), ResponseEnum.INVALID_USER.getCode(), ResponseEnum.INVALID_USER.getDisplayDesc());
            }

        } catch (DomainException | ForbiddenException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.USER_LOGIN_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    @Override
    public UserTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ServerException, UserValidationException {

        try {
            log.debug(LoggingConstants.REFRESH_TOKEN_LOG, "Refresh Access Token", LoggingConstants.STARTED);
            Map<String, User> tokenUserData = new HashMap<>();

            if (isTokenValid(refreshTokenRequest.getRefreshToken(), TokenType.REFRESH_TOKEN, tokenUserData)) {
                TokenDto tokenDto = tokenService.populateTokenValues(tokenUserData.get(AppConstants.USER_DATA), false, refreshTokenRequest.getRefreshToken());

                UserDetails userDetails = getUserDetails(tokenUserData.get(AppConstants.USER_DATA));
                log.debug(LoggingConstants.REFRESH_TOKEN_LOG, "Refresh Access Token", LoggingConstants.ENDED);

                return UserTokenResponse.builder()
                        .userDetails(userDetails)
                        .tokenDto(tokenDto)
                        .baseResponse(getSuccessBaseResponse("Token Refreshed Successfully"))
                        .build();

            } else {
                log.error(LoggingConstants.REFRESH_TOKEN_ERROR, ResponseEnum.REFRESH_TOKEN_INVALID.getDesc(), ResponseEnum.REFRESH_TOKEN_INVALID.getDesc(), null);
                throw new UserValidationException(ResponseEnum.REFRESH_TOKEN_INVALID.getDesc(), ResponseEnum.REFRESH_TOKEN_INVALID.getCode(), ResponseEnum.REFRESH_TOKEN_INVALID.getDisplayDesc());
            }

        } catch (UserValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.REFRESH_TOKEN_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    @Override
    public boolean isTokenValid(String token, TokenType tokenType, Map<String, User> tokenUserData) {

        try {
            log.info(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, tokenType.name());
            Map<String, Object> claimsMap = jwtService.getAllClaimsFromTokenAsMap(token);

            String email = (String) claimsMap.get(AppConstants.EMAIL);
            String sessionId = (String) claimsMap.get(AppConstants.ACTIVE_SESSION_ID);
            User userToAuthenticate = userRepository.findByEmail(email);

            if (Objects.nonNull(userToAuthenticate) && Objects.nonNull(userToAuthenticate.getActiveSessionId())
                    && userToAuthenticate.getActiveSessionId().equals(sessionId)) {

                if (!jwtService.isTokenExpired(token)) {
                    if (TokenType.REFRESH_TOKEN.equals(tokenType)) {
                        tokenUserData.put(AppConstants.USER_DATA, userToAuthenticate);
                    }
                    return true;

                } else {
                    log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "Token is expired");
                }

            } else {
                log.debug(LoggingConstants.TOKEN_LOG, AppConstants.VALIDATING_TOKEN, "User not found or session Id is not valid");
            }

        } catch (Exception ex) {
            log.error(LoggingConstants.TOKEN_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            return false;
        }

        return false;
    }

    private void validateEnteredPassword(String passwordEntered, String hashedStoredPassword) throws ForbiddenException {

        if (!passwordService.authenticate(passwordEntered.toCharArray(), hashedStoredPassword)) {
            log.error(LoggingConstants.USER_LOGIN_ERROR, ResponseEnum.INCORRECT_PASSWORD.getDesc(), ResponseEnum.INCORRECT_PASSWORD.getDesc(), null);
            throw new ForbiddenException(ResponseEnum.INCORRECT_PASSWORD.getDesc(), ResponseEnum.INCORRECT_PASSWORD.getCode(), ResponseEnum.INCORRECT_PASSWORD.getDisplayDesc());
        }
    }

    private UserDetails getUserDetails(User user) {
        return UserDetails.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }
}
