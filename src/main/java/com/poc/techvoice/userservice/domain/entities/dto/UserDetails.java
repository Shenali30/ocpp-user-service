package com.poc.techvoice.userservice.domain.entities.dto;

import com.poc.techvoice.userservice.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetails {

    private String email;
    private Role role;
}
