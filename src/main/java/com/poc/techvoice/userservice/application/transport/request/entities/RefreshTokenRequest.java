package com.poc.techvoice.userservice.application.transport.request.entities;

import com.poc.techvoice.userservice.application.validator.RequestEntityInterface;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest implements RequestEntityInterface {

    @NotBlank
    private String refreshToken;
}
