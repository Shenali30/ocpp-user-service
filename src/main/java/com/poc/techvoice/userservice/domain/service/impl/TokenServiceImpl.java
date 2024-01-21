package com.poc.techvoice.userservice.domain.service.impl;

import com.poc.techvoice.userservice.application.constants.AppConstants;
import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.TokenDto;
import com.poc.techvoice.userservice.domain.enums.TokenType;
import com.poc.techvoice.userservice.domain.service.JwtService;
import com.poc.techvoice.userservice.domain.service.TokenService;
import com.poc.techvoice.userservice.domain.util.UtilityService;
import com.poc.techvoice.userservice.external.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TokenServiceImpl extends UtilityService implements TokenService {

    @Value("${security.jwt.expire-time.access-token.millisecond}")
    private Integer accessTokenExpireTime;
    @Value("${security.jwt.refresh-token-in.millisecond}")
    private Integer refreshTokenInTime;


    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenServiceImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public TokenDto generateUserTokens(User user) throws ServerException {

        try {
            log.debug(LoggingConstants.TOKEN_GENERATION_LOG, AppConstants.TOKEN_GENERATION, LoggingConstants.STARTED);

            String activeSessionId = UUID.randomUUID().toString();
            user.setActiveSessionId(activeSessionId);
            userRepository.save(user);

            log.debug(LoggingConstants.TOKEN_GENERATION_LOG, AppConstants.TOKEN_GENERATION, "Saved Active Session Id for User");
            return populateTokenValues(user, true, null);

        } catch (Exception ex) {
            log.error(LoggingConstants.TOKEN_GENERATION_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

    @Override
    public TokenDto populateTokenValues(User user, boolean isNewLogin, String refreshToken) {

        Date refreshTokenAt = new Date(System.currentTimeMillis() + refreshTokenInTime);
        String refreshTokenAtString = convertDateToString(refreshTokenAt);

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(jwtService.generateToken(user, TokenType.ACCESS_TOKEN))
                .refreshTokenAt(refreshTokenAtString)
                .expiresIn(accessTokenExpireTime)
                .build();

        if (isNewLogin) {
            tokenDto.setRefreshToken(jwtService.generateToken(user, TokenType.REFRESH_TOKEN));
        } else {
            tokenDto.setRefreshToken(refreshToken);
        }

        log.debug(LoggingConstants.TOKEN_GENERATION_LOG, AppConstants.TOKEN_GENERATION, LoggingConstants.ENDED);
        return tokenDto;

    }
}
