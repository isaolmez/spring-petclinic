package com.isa.spring.mvc.petclinic.data.formatter;

import com.isa.spring.mvc.petclinic.data.model.PetType;
import com.isa.spring.mvc.petclinic.data.repository.PetTypeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeRepository petTypeRepository;

    public PetTypeFormatter(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        List<PetType> types = petTypeRepository.findAll();
        for (PetType type : types) {
            if (StringUtils.equals(type.getName(), text)) {
                return type;
            }
        }

        throw new ParseException("Cannot parse " + text, 0);
    }

    @Override
    public String print(PetType type, Locale locale) {
        return type.getName();
    }
}
