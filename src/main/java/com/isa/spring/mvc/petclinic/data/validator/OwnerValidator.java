package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.validator.core.PersonValidator;
import org.springframework.validation.Errors;

public class OwnerValidator extends PersonValidator {

    @Override
    protected boolean supportsInternal(Class<?> clazz) {
        return Owner.class.isAssignableFrom(clazz);
    }

    @Override
    protected void validateInternal(Object target, Errors errors) {

    }
}
