package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OwnerValidatorTest {

    private Validator validator = createValidator();

    @Test
    public void shouldPassValidation() {
        Owner owner = new Owner();
        owner.setFirstName("Test First Name");
        owner.setLastName("Test Last Name");

        Set<ConstraintViolation<Owner>> violations = validator.validate(owner);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotPassValidation() {
        Owner owner = new Owner();

        Set<ConstraintViolation<Owner>> violations = validator.validate(owner);

        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName")))
                .isTrue();
        assertThat(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("lastName")))
                .isTrue();
    }

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
