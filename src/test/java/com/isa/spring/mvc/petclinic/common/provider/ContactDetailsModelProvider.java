package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.embeddable.ContactDetails;

import java.util.UUID;

public class ContactDetailsModelProvider implements TestModelProvider<ContactDetails> {

    public static final ContactDetailsModelProvider INSTANCE = new ContactDetailsModelProvider();

    private ContactDetailsModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public ContactDetails randomModel() {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setTelephone("123456789");
        contactDetails.setMobilePhone("123456789");
        contactDetails.setEmailAddress(String.format("test-%s@test.com", UUID.randomUUID().toString()));
        return contactDetails;
    }

    @Override
    public ContactDetails sameModel() {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setTelephone("123456789");
        contactDetails.setMobilePhone("123456789");
        contactDetails.setEmailAddress("test@test.com");
        return contactDetails;
    }
}
