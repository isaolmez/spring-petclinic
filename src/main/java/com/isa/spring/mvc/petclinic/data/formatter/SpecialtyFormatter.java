package com.isa.spring.mvc.petclinic.data.formatter;

import com.isa.spring.mvc.petclinic.data.model.Specialty;
import com.isa.spring.mvc.petclinic.data.repository.SpecialtyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Component
public class SpecialtyFormatter implements Formatter<Specialty> {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyFormatter(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        List<Specialty> specialties = specialtyRepository.findAll();
        for (Specialty specialty : specialties) {
            if (StringUtils.equals(specialty.getName(), text)) {
                return specialty;
            }
        }

        throw new ParseException("Cannot parse " + text, 0);
    }

    @Override
    public String print(Specialty specialty, Locale locale) {
        return specialty.getName();
    }
}
