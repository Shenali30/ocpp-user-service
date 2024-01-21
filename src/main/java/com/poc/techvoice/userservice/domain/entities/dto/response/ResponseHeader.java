package com.poc.techvoice.userservice.domain.entities.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseHeader {

    private String responseCode;
    private String responseDesc;
    private String displayDesc;
    private String timestamp;
}
