package com.isa.spring.mvc.petclinic.service;

import com.isa.spring.mvc.petclinic.data.model.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> findAllByClinicId(long clinicId);

    List<Owner> findAll();

    Owner findOne(long id);

    Owner save(Owner clinic);

    void delete(long id);
}
