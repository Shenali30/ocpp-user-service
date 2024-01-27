package com.poc.techvoice.userservice.domain.entities.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private ResponseHeader responseHeader;
}
