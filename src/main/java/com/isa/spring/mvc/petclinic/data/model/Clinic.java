package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.NamedEntity;
import com.isa.spring.mvc.petclinic.data.model.embeddable.Address;
import com.isa.spring.mvc.petclinic.data.model.embeddable.ContactDetails;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clinics")
public class Clinic extends NamedEntity {
    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contact;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Owner> owners;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Veterinarian> veterinarians;

    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContact() {
        if (contact == null) {
            contact = new ContactDetails();
        }

        return contact;
    }

    public void setContact(ContactDetails contact) {
        this.contact = contact;
    }

    public Set<Owner> getOwners() {
        return Collections.unmodifiableSet(getOwnersInternal());
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public Set<Veterinarian> getVeterinarians() {
        return Collections.unmodifiableSet(getVeterinariansInternal());
    }

    private void setVeterinarians(Set<Veterinarian> veterinarians) {
        this.veterinarians = veterinarians;
    }

    public void addOwner(Owner owner) {
        getOwnersInternal().add(owner);
        owner.setClinic(this);
    }

    public void removeOwner(Owner owner) {
        getOwnersInternal().remove(owner);
        owner.setClinic(null);
    }

    public void addVeterinarian(Veterinarian veterinarian) {
        getVeterinariansInternal().add(veterinarian);
        veterinarian.setClinic(this);
    }

    public void removeVeterinarian(Veterinarian veterinarian) {
        getVeterinariansInternal().remove(veterinarian);
        veterinarian.setClinic(null);
    }

    public Set<Owner> getOwnersInternal() {
        if (this.owners == null) {
            this.owners = new HashSet<>();
        }

        return this.owners;
    }

    private Set<Veterinarian> getVeterinariansInternal() {
        if (this.veterinarians == null) {
            this.veterinarians = new HashSet<>();
        }

        return this.veterinarians;
    }
}
