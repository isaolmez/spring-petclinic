package com.isa.spring.mvc.petclinic.data.validator.core;


import com.isa.spring.mvc.petclinic.data.model.core.NamedEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class NamedEntityValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public void validate(Object target, Errors errors) {
        NamedEntity namedEntity = (NamedEntity) target;
        if (StringUtils.isBlank(namedEntity.getName())) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }

        validateInternal(target, errors);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NamedEntity.class.isAssignableFrom(clazz) && supportsInternal(clazz);
    }

    protected abstract boolean supportsInternal(Class<?> clazz);

    protected abstract void validateInternal(Object target, Errors errors);
}
