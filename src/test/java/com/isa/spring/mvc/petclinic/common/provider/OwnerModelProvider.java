package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Owner;

import java.util.UUID;

public class OwnerModelProvider implements TestModelProvider<Owner> {

    public static final OwnerModelProvider INSTANCE = new OwnerModelProvider();

    private OwnerModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Owner randomModel() {
        Owner owner = new Owner();
        owner.setFirstName("Test First Name-" + UUID.randomUUID().toString());
        owner.setLastName("Test Last Name-" + UUID.randomUUID().toString());
        owner.setAddress(AddressModelProvider.INSTANCE.randomModel());
        owner.setContact(ContactDetailsModelProvider.INSTANCE.randomModel());
        return owner;
    }

    @Override
    public Owner sameModel() {
        Owner owner = new Owner();
        owner.setFirstName("Test first name");
        owner.setLastName("Test last name");
        owner.setAddress(AddressModelProvider.INSTANCE.sameModel());
        owner.setContact(ContactDetailsModelProvider.INSTANCE.sameModel());
        return owner;
    }
}
