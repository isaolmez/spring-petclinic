package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.data.model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}
