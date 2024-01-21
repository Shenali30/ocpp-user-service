package com.poc.techvoice.userservice.domain.service.impl;

import com.poc.techvoice.userservice.application.constants.AppConstants;
import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.enums.TokenType;
import com.poc.techvoice.userservice.domain.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.sign-key}")
    private String jwtSignKey;
    @Value("${security.jwt.expire-time.access-token.millisecond}")
    private Integer accessTokenExpireTime;

    @Value("${security.jwt.expire-time.refresh-token.hour}")
    private Integer refreshTokenExpiryTime;

    @Override
    public String generateToken(User user, TokenType tokenType) {

        Map<String, Object> claims = new HashMap<>();
        Date expiredAt = null;

        claims.put(AppConstants.ROLE, user.getRole().name());
        claims.put(AppConstants.EMAIL, user.getEmail());
        claims.put(AppConstants.ACTIVE_SESSION_ID, user.getActiveSessionId());

        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            expiredAt = new Date(System.currentTimeMillis() + accessTokenExpireTime);

        } else if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
            long refreshExpiryTimeInMills = TimeUnit.HOURS.toMillis(refreshTokenExpiryTime);
            expiredAt = new Date(System.currentTimeMillis() + refreshExpiryTimeInMills);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(tokenType.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, jwtSignKey)
                .compact();
    }

    @Override
    public Map<String, Object> getAllClaimsFromTokenAsMap(String token) {

        log.debug(LoggingConstants.TOKEN_LOG, "Extracting all claims", null);
        Claims allClaims = getAllClaims(token);
        return new HashMap<>(allClaims);
    }

    @Override
    public boolean isTokenExpired(String authToken) {

        if(authToken.contains(AppConstants.BEARER)){
            authToken = authToken.substring(7);
        }

        Date expirationDateTime = getExpirationDateFromToken(authToken);
        return expirationDateTime.before(new Date());
    }

    private Date getExpirationDateFromToken(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token){
        return Jwts.parser().setSigningKey(jwtSignKey).parseClaimsJws(token).getBody();
    }
}
