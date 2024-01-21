package com.poc.techvoice.userservice.application.transport.request.entities;

import com.poc.techvoice.userservice.application.validator.RequestEntityInterface;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserCreateRequest implements RequestEntityInterface {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
