package com.poc.techvoice.userservice.domain.service.impl;

import com.poc.techvoice.userservice.application.constants.LoggingConstants;
import com.poc.techvoice.userservice.application.enums.ResponseEnum;
import com.poc.techvoice.userservice.application.exception.type.ServerException;
import com.poc.techvoice.userservice.application.transport.request.entities.UserCreateRequest;
import com.poc.techvoice.userservice.domain.entities.User;
import com.poc.techvoice.userservice.domain.entities.dto.response.BaseResponse;
import com.poc.techvoice.userservice.domain.enums.Channel;
import com.poc.techvoice.userservice.domain.enums.Role;
import com.poc.techvoice.userservice.domain.exception.DomainException;
import com.poc.techvoice.userservice.domain.service.UserAccountService;
import com.poc.techvoice.userservice.domain.util.PasswordService;
import com.poc.techvoice.userservice.domain.util.UtilityService;
import com.poc.techvoice.userservice.external.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserAccountServiceImpl extends UtilityService implements UserAccountService {

    private final PasswordService passwordService;
    private final UserRepository userRepository;

    @Override
    public BaseResponse createNewUser(UserCreateRequest request) throws ServerException, DomainException {

        try {
            log.debug(LoggingConstants.USER_CREATE_LOG, "Create new user", LoggingConstants.STARTED);
            if (!userRepository.existsByEmail(request.getEmail())) {

                /*
                 * all new users will be considered as readers at the point of sign-up
                 * the notification channel for all new users will be set to email by default
                 */

                User newUser = User.builder()
                        .email(request.getEmail())
                        .password(passwordService.hash(request.getPassword().toCharArray()))
                        .role(Role.READER)
                        .notificationChannel(Channel.EMAIL)
                        .build();

                userRepository.save(newUser);
                log.debug(LoggingConstants.USER_CREATE_LOG, "Create new user", LoggingConstants.ENDED);
                return getSuccessBaseResponse("User Created Successfully! Please sign in using the credentials you provided");

            } else {
                log.error(LoggingConstants.USER_CREATE_ERROR, ResponseEnum.USER_ALREADY_EXISTS.getDesc(), ResponseEnum.USER_ALREADY_EXISTS.getDesc(), null);
                throw new DomainException(ResponseEnum.USER_ALREADY_EXISTS.getDesc(), ResponseEnum.USER_ALREADY_EXISTS.getCode(), ResponseEnum.USER_ALREADY_EXISTS.getDisplayDesc());
            }

        } catch (DomainException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(LoggingConstants.USER_CREATE_ERROR, ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getDesc(), ex.getStackTrace());
            throw new ServerException(ex.getMessage(), ResponseEnum.INTERNAL_ERROR.getCode(), ResponseEnum.INTERNAL_ERROR.getDisplayDesc());
        }

    }

}
