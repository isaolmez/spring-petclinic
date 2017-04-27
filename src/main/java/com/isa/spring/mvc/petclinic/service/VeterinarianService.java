package com.isa.spring.mvc.petclinic.service;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;

import java.util.List;

public interface VeterinarianService {

    List<Veterinarian> findAllByClinicId(long clinicId);

    List<Veterinarian> findAll();

    Veterinarian findOne(long id);

    Veterinarian save(Veterinarian veterinarian);

    void delete(long id);
}
