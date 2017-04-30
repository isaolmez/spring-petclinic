package com.isa.spring.mvc.petclinic.data.validator;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ClinicValidatorTest {

    private ClinicValidator clinicValidator = new ClinicValidator();

    @Test
    public void shouldPassValidation(){
        Clinic clinic = new Clinic();
        clinic.setName("Test");
        Errors errors = new BeanPropertyBindingResult(clinic, "clinic");

        clinicValidator.validate(clinic, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidation(){
        Clinic clinic = new Clinic();
        Errors errors = new BeanPropertyBindingResult(clinic, "clinic");

        clinicValidator.validate(clinic, errors);

        assertEquals(1, errors.getErrorCount());
    }
}
