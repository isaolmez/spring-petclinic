package com.isa.spring.mvc.petclinic.common.provider;


import com.isa.spring.mvc.petclinic.data.model.embeddable.Address;

import java.util.UUID;

public class AddressModelProvider implements TestModelProvider<Address> {

    public static final AddressModelProvider INSTANCE = new AddressModelProvider();

    private AddressModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Address randomModel() {
        Address address = new Address();
        address.setAddress("Test Address-" + UUID.randomUUID().toString());
        address.setCity("Test City");
        address.setCountry("Test Country");
        address.setPostcode("Test Postcode");
        return address;
    }

    @Override
    public Address sameModel() {
        Address address = new Address();
        address.setAddress("Test Address");
        address.setCity("Test City");
        address.setCountry("Test Country");
        address.setPostcode("Test Postcode");
        return address;
    }
}
