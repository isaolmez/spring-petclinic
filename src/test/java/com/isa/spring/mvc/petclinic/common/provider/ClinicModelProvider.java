package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Clinic;

import java.util.Date;
import java.util.UUID;

public class ClinicModelProvider implements TestModelProvider<Clinic> {
    public static final ClinicModelProvider INSTANCE = new ClinicModelProvider();

    private final Date date = new Date();

    private ClinicModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Clinic randomModel() {
        Clinic clinic = new Clinic();
        clinic.setName("Test Name-" + UUID.randomUUID().toString());
        clinic.setAddress(AddressModelProvider.INSTANCE.randomModel());
        clinic.setContact(ContactDetailsModelProvider.INSTANCE.randomModel());
        clinic.setCreatedAt(new Date());
        clinic.setUpdatedAt(new Date());
        return clinic;
    }

    @Override
    public Clinic sameModel() {
        Clinic clinic = new Clinic();
        clinic.setName("Test Name");
        clinic.setAddress(AddressModelProvider.INSTANCE.sameModel());
        clinic.setContact(ContactDetailsModelProvider.INSTANCE.sameModel());
        clinic.setCreatedAt(date);
        clinic.setUpdatedAt(date);
        return clinic;
    }
}
