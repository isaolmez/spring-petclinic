package com.isa.spring.mvc.petclinic.data.validator;


import com.isa.spring.mvc.petclinic.data.model.Clinic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClinicValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public void validate(Object target, Errors errors) {
        Clinic clinic = (Clinic) target;
        if (StringUtils.isBlank(clinic.getName())) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Clinic.class.isAssignableFrom(clazz);
    }
}
