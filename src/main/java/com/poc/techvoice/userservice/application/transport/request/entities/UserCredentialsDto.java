package com.poc.techvoice.userservice.application.transport.request.entities;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserCredentialsDto {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
