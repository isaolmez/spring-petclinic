package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.NamedEntity;
import com.isa.spring.mvc.petclinic.data.model.core.TimestampedEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @ManyToOne
    private Owner owner;

    @ManyToOne
    private PetType type;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Visit> visits;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public Set<Visit> getVisits() {
        return Collections.unmodifiableSet(getVisits());
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(Visit visit){
        getVisitsInternal().add(visit);
        visit.setPet(this);
    }

    public void removeVisit(Visit visit){
        getVisitsInternal().remove(visit);
        visit.setPet(null);
    }

    private Set<Visit> getVisitsInternal(){
        if(this.visits == null){
            this.visits = new HashSet<>();
        }

        return this.visits;
    }
}
