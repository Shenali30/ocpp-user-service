package com.poc.techvoice.userservice.domain.service;

import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserCreateRequest;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.exception.DomainException;

public interface UserAccountService {

    BaseResponse createNewUser(UserCreateRequest request) throws ServerException, DomainException;
}
