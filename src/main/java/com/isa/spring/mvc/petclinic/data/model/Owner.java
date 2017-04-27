package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.Person;
import com.isa.spring.mvc.petclinic.data.model.core.TimestampedEntity;
import com.isa.spring.mvc.petclinic.data.model.embeddable.Address;
import com.isa.spring.mvc.petclinic.data.model.embeddable.ContactDetails;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner extends Person {
    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contact;

    @ManyToOne
    private Clinic clinic;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pet> pets;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContact() {
        return contact;
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

    public Set<Pet> getPets() {
        return Collections.unmodifiableSet(getPetsInternal());
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet){
        getPetsInternal().add(pet);
        pet.setOwner(this);
    }

    public void removePet(Pet pet){
        getPetsInternal().remove(pet);
        pet.setOwner(null);
    }

    private Set<Pet> getPetsInternal(){
        if(this.pets == null){
            this.pets = new HashSet<>();
        }

        return this.pets;
    }
}
