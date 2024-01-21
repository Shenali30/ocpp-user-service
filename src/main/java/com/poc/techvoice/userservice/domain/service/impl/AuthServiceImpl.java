package com.poc.techvoice.userservice.domain.service.impl;

import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.application.exception.type.ForbiddenException;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserLoginRequest;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.TokenDto;
import com.poc.techvoice.userservice.domain.entities.dto.UserDetails;
import com.poc.techvoice.userservice.domain.entities.dto.response.UserTokenResponse;
import com.poc.techvoice.userservice.domain.exception.DomainException;
import com.poc.techvoice.userservice.domain.service.AuthService;
import com.poc.techvoice.userservice.domain.service.TokenService;
import com.poc.techvoice.userservice.domain.util.PasswordService;
import com.poc.techvoice.userservice.domain.util.UtilityService;
import com.poc.techvoice.userservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl extends UtilityService implements AuthService {

    private final PasswordService passwordService;
    private final TokenService tokenService;
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
