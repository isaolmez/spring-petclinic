package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.NamedEntity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {
    @ManyToMany(mappedBy = "specialties", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Veterinarian> veterinarians;

    public Set<Veterinarian> getVeterinarians() {
        return Collections.unmodifiableSet(getVeterinariansInternal());
    }

    public void setVeterinarians(Set<Veterinarian> veterinarians) {
        this.veterinarians = veterinarians;
    }

    public void addVeterinarian(Veterinarian veterinarian) {
        this.getVeterinariansInternal().add(veterinarian);
        veterinarian.getSpecialtiesInternal().add(this);
    }

    public void removeVeterinarians() {
        for (Veterinarian veterinarian : getVeterinarians()) {
            removeVeterinarian(veterinarian);
        }
    }

    public void removeVeterinarian(Veterinarian veterinarian) {
        this.getVeterinariansInternal().remove(veterinarian);
        veterinarian.getSpecialtiesInternal().remove(this);
    }

    Set<Veterinarian> getVeterinariansInternal() {
        if (this.veterinarians == null) {
            this.veterinarians = new HashSet<>();
        }

        return this.veterinarians;
    }
}
