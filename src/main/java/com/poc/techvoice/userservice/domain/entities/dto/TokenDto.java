package com.poc.techvoice.userservice.domain.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String accessToken;
    private int expiresIn;
    private String refreshTokenAt;
    private String refreshToken;
}
