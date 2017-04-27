package com.isa.spring.mvc.petclinic.service;

import com.isa.spring.mvc.petclinic.data.model.Clinic;

import java.util.List;

public interface ClinicService {

    List<Clinic> findAll();

    Clinic findOne(long id);

    Clinic save(Clinic clinic);

    void delete(long id);
}
