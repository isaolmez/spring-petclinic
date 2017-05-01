package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ClinicValidatorTest {

    private Validator validator = createValidator();

    @Test
    public void shouldPassValidation(){
        Clinic clinic = new Clinic();
        clinic.setName("Test");

        Set<ConstraintViolation<Clinic>> violations = validator.validate(clinic);

        Assertions.assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotPassValidation(){
        Clinic clinic = new Clinic();

        Set<ConstraintViolation<Clinic>> violations = validator.validate(clinic);

        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<Clinic> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");
    }

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
