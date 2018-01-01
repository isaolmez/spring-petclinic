package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.PetType;
import java.util.UUID;

public class PetTypeModelProvider implements TestModelProvider<PetType> {

    public static final PetTypeModelProvider INSTANCE = new PetTypeModelProvider();

    private PetTypeModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public PetType randomModel() {
        PetType petType = new PetType();
        petType.setName("Test Name-" + UUID.randomUUID().toString());
        return petType;
    }

    @Override
    public PetType sameModel() {
        PetType petType = new PetType();
        petType.setName("test type");
        return petType;
    }
}
