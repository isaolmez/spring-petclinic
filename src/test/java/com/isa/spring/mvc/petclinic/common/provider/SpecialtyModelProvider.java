package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Specialty;
import java.util.UUID;

public class SpecialtyModelProvider implements TestModelProvider<Specialty> {
    public static final SpecialtyModelProvider INSTANCE = new SpecialtyModelProvider();

    private SpecialtyModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Specialty randomModel() {
        Specialty specialty = new Specialty();
        specialty.setName("Test Specialty-" + UUID.randomUUID().toString());
        return specialty;
    }

    @Override
    public Specialty sameModel() {
        Specialty specialty = new Specialty();
        specialty.setName("Test Specialty");
        return specialty;
    }
}
