package com.isa.spring.mvc.petclinic.data.validator.core;

import com.isa.spring.mvc.petclinic.data.model.core.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class PersonValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (StringUtils.isBlank(person.getFirstName())) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        if (StringUtils.isBlank(person.getLastName())) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }

        validateInternal(target, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz) && supportsInternal(clazz);
    }

    protected abstract boolean supportsInternal(Class<?> clazz);

    protected abstract void validateInternal(Object target, Errors errors);
}
