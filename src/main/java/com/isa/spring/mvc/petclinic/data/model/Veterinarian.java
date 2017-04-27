package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.Person;
import com.isa.spring.mvc.petclinic.data.model.embeddable.Address;
import com.isa.spring.mvc.petclinic.data.model.embeddable.ContactDetails;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vets")
public class Veterinarian extends Person {
    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contact;

    @ManyToOne
    private Clinic clinic;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"))
    private Set<Specialty> specialties;

    public Address getAddress() {
        return address == null ? new Address() : address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContact() {
        return contact == null ? new ContactDetails() : contact;
    }

    public void setContact(ContactDetails contact) {
        this.contact = contact;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public void removeSpecialties() {
        for (Specialty specialty : getSpecialties()) {
            removeSpecialty(specialty);
        }
    }

    public void removeSpecialty(Specialty specialty) {
        this.getSpecialties().remove(specialty);
        specialty.getVeterinarians().remove(this);
    }
}
