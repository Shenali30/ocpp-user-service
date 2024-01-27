package com.poc.techvoice.userservice.application.validator;

import com.poc.techvoice.userservice.application.exception.type.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class RequestEntityValidator {

    private final Validator validator;

    public void validate(RequestEntityInterface target) {
        Set<ConstraintViolation<RequestEntityInterface>> errors = this.validator.validate(target);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

}
