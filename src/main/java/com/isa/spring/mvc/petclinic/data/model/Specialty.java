package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.NamedEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {
    @ManyToMany(mappedBy = "specialties", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Veterinarian> veterinarians;

    public Set<Veterinarian> getVeterinarians() {
        return veterinarians;
    }

    public void setVeterinarians(Set<Veterinarian> veterinarians) {
        this.veterinarians = veterinarians;
    }

    public void removeVeterinarians() {
        for (Veterinarian veterinarian : getVeterinarians()) {
            removeVeterinarian(veterinarian);
        }
    }

    public void removeVeterinarian(Veterinarian veterinarian) {
        this.getVeterinarians().remove(veterinarian);
        veterinarian.getSpecialties().remove(this);
    }
}
