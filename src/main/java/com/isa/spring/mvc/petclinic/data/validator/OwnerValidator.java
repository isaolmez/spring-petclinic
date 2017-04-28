package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.validator.core.PersonValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OwnerValidator extends PersonValidator {

    @Override
    protected boolean supportsInternal(Class<?> clazz) {
        return Owner.class.isAssignableFrom(clazz);
    }

    @Override
    protected void validateInternal(Object target, Errors errors) {

    }
}
