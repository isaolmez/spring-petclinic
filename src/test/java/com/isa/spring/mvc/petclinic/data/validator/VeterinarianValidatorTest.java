package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class VeterinarianValidatorTest {

    private Validator validator = createValidator();

    @Test
    public void shouldPassValidation() {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test First Name");
        veterinarian.setLastName("Test Last Name");

        Set<ConstraintViolation<Veterinarian>> violations = validator.validate(veterinarian);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotPassValidation() {
        Veterinarian veterinarian = new Veterinarian();

        Set<ConstraintViolation<Veterinarian>> violations = validator.validate(veterinarian);

        assertThat(violations.size()).isEqualTo(2);
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
