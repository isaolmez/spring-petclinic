package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
