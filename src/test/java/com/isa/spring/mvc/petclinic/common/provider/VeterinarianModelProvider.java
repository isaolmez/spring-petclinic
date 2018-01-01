package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import java.util.UUID;

public class VeterinarianModelProvider implements TestModelProvider<Veterinarian> {

    public static final VeterinarianModelProvider INSTANCE = new VeterinarianModelProvider();

    private VeterinarianModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Veterinarian randomModel() {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test First Name" + UUID.randomUUID().toString());
        veterinarian.setLastName("Test Last Name" + UUID.randomUUID().toString());
        veterinarian.setAddress(AddressModelProvider.INSTANCE.randomModel());
        veterinarian.setContact(ContactDetailsModelProvider.INSTANCE.randomModel());
        return veterinarian;
    }

    @Override
    public Veterinarian sameModel() {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test First Name");
        veterinarian.setLastName("Test Last Name");
        veterinarian.setAddress(AddressModelProvider.INSTANCE.sameModel());
        veterinarian.setContact(ContactDetailsModelProvider.INSTANCE.sameModel());
        return veterinarian;
    }
}
