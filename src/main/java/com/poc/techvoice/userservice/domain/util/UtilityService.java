package com.poc.techvoice.userservice.domain.util;

import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.entities.dto.response.ResponseHeader;

import java.time.LocalDateTime;

public class UtilityService {

    protected ResponseHeader getSuccessResponseHeader(String successDisplayMessage) {

        return ResponseHeader.builder()
                .responseCode(ResponseEnum.SUCCESS.getCode())
                .responseDesc(ResponseEnum.SUCCESS.getDesc())
                .timestamp(LocalDateTime.now().toString())
                .displayDesc(successDisplayMessage)
                .build();
    }

    protected BaseResponse getSuccessBaseResponse(String successDisplayMessage) {
        return BaseResponse.builder()
                .responseHeader(getSuccessResponseHeader(successDisplayMessage))
                .build();
    }
}
