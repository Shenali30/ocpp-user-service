package com.poc.techvoice.userservice.domain.entities.dto.response;

import com.poc.techvoice.userservice.domain.entities.dto.TokenDto;
import com.poc.techvoice.userservice.domain.entities.dto.UserDetails;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenResponse {

    private UserDetails userDetails;
    private TokenDto tokenDto;
    private BaseResponse baseResponse;
}
