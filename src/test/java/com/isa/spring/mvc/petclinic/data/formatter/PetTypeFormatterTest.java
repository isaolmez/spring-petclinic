package com.isa.spring.mvc.petclinic.data.formatter;

import com.isa.spring.mvc.petclinic.data.model.PetType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetTypeFormatterTest {

    @Autowired
    private PetTypeFormatter petTypeFormatter;

    private PetType petType;

    private String petTypeString = "bird";

    @Before
    public void setUp(){
        petType = new PetType();
        petType.setName(petTypeString);
    }

    @Test
    public void shouldConvertFromPetTypeToString(){
        final String expected = petTypeString;

        String actual = petTypeFormatter.print(petType, null);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertFromStringToPetType(){
        final String expected = petTypeString;

        PetType actual = null;
        try {
            actual = petTypeFormatter.parse(petTypeString, null);
        } catch (ParseException e) {
            fail();
        }

        assertNotNull(actual);
        assertEquals(expected, actual.getName());
    }
}
