package com.isa.spring.mvc.petclinic.data.model;

import com.isa.spring.mvc.petclinic.data.model.core.TimestampedEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
public class Visit extends TimestampedEntity {

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate;

    @Column
    private String description;

    @ManyToOne
    private Pet pet;

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
