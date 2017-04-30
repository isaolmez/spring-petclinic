package com.isa.spring.mvc.petclinic.data.formatter;

import com.isa.spring.mvc.petclinic.data.model.Specialty;
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
public class SpecialtyFormatterTest {

    @Autowired
    private SpecialtyFormatter specialtyFormatter;

    private Specialty specialty;

    private String specialtyString = "surgery";

    @Before
    public void setUp(){
        specialty = new Specialty();
        specialty.setName(specialtyString);
    }

    @Test
    public void shouldConvertFromPetTypeToString(){
        final String expected = specialtyString;

        String actual = specialtyFormatter.print(specialty, null);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertFromStringToPetType(){
        final String expected = specialtyString;

        Specialty actual = null;
        try {
            actual = specialtyFormatter.parse(specialtyString, null);
        } catch (ParseException e) {
            fail();
        }

        assertNotNull(actual);
        assertEquals(expected, actual.getName());
    }
}
