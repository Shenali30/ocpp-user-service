package com.poc.techvoice.userservice.domain.util;

import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.entities.dto.response.ResponseHeader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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

    protected String convertDateToString(Date date) {
        String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateFormat df = new SimpleDateFormat(datePattern);
        return df.format(date);
    }
}
