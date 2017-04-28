package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import com.isa.spring.mvc.petclinic.data.validator.core.PersonValidator;
import org.springframework.validation.Errors;

public class VeterinarianValidator extends PersonValidator {

    @Override
    protected boolean supportsInternal(Class<?> clazz) {
        return Veterinarian.class.isAssignableFrom(clazz);
    }

    @Override
    protected void validateInternal(Object target, Errors errors) {

    }
}
