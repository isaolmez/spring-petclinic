package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
    @Query("select v from Veterinarian v where v.clinic.id = :clinicId")
    List<Veterinarian> findAllByClinicId(@Param("clinicId") long clinicId);
}
