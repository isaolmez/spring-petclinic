package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class VeterinarianValidatorTest {
    private VeterinarianValidator veterinarianValidator = new VeterinarianValidator();

    @Test
    public void shouldPassValidation() {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test First Name");
        veterinarian.setLastName("Test Last Name");
        Errors errors = new BeanPropertyBindingResult(veterinarian, "veterinarian");

        veterinarianValidator.validate(veterinarian, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidation() {
        Veterinarian veterinarian = new Veterinarian();
        Errors errors = new BeanPropertyBindingResult(veterinarian, "veterinarian");

        veterinarianValidator.validate(veterinarian, errors);

        assertEquals(2, errors.getErrorCount());
    }
}
