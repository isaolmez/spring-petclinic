package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("select o from Owner o where o.clinic.id = :clinicId")
    List<Owner> findAllByClinicId(@Param("clinicId") long clinicId);
}
