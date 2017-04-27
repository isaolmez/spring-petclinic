package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Pet;

import java.util.Date;
import java.util.UUID;

public class PetModelProvider implements TestModelProvider<Pet> {
    public static final PetModelProvider INSTANCE = new PetModelProvider();

    private final Date date = new Date();

    private PetModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Pet randomModel() {
        Pet pet = new Pet();
        pet.setName("Test Name" + UUID.randomUUID().toString());
        pet.setBirthDate(new Date());
        return pet;
    }

    @Override
    public Pet sameModel() {
        Pet pet = new Pet();
        pet.setName("Test name");
        pet.setBirthDate(date);
        return pet;
    }
}
