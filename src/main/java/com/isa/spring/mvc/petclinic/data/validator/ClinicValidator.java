package com.isa.spring.mvc.petclinic.data.validator;


import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.validator.core.NamedEntityValidator;
import org.springframework.validation.Errors;

public class ClinicValidator extends NamedEntityValidator {
    @Override
    protected boolean supportsInternal(Class<?> clazz) {
        return Clinic.class.isAssignableFrom(clazz);
    }

    @Override
    protected void validateInternal(Object target, Errors errors) {
    }
}
