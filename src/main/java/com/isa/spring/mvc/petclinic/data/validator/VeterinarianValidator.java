package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VeterinarianValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public void validate(Object target, Errors errors) {
        Veterinarian veterinarian = (Veterinarian) target;
        if (StringUtils.isBlank(veterinarian.getFirstName())) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        if (StringUtils.isBlank(veterinarian.getLastName())) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Veterinarian.class.isAssignableFrom(clazz);
    }
}
