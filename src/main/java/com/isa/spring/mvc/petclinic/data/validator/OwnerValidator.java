package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OwnerValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public void validate(Object target, Errors errors) {
        Owner owner = (Owner) target;
        if (StringUtils.isBlank(owner.getFirstName())) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        if (StringUtils.isBlank(owner.getLastName())) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Owner.class.isAssignableFrom(clazz);
    }
}
