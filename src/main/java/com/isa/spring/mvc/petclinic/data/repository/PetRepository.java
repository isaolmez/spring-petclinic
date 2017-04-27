package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.data.model.Pet;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p from Pet p where p.owner.id = :ownerId")
    List<Pet> findAllByOwnerId(@Param("ownerId") long ownerId);
}
