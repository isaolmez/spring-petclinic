package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class OwnerValidatorTest {
    private OwnerValidator ownerValidator = new OwnerValidator();

    @Test
    public void shouldPassValidation(){
        Owner owner = new Owner();
        owner.setFirstName("Test First Name");
        owner.setLastName("Test Last Name");
        Errors errors = new BeanPropertyBindingResult(owner, "owner");

        ownerValidator.validate(owner, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidation(){
        Owner owner = new Owner();
        Errors errors = new BeanPropertyBindingResult(owner, "owner");

        ownerValidator.validate(owner, errors);

        assertEquals(2, errors.getErrorCount());
    }
}
