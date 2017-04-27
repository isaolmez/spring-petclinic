package com.isa.spring.mvc.petclinic.service.impl;

import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import com.isa.spring.mvc.petclinic.data.repository.VeterinarianRepository;
import com.isa.spring.mvc.petclinic.service.VeterinarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarianServiceImpl implements VeterinarianService{

    private final VeterinarianRepository veterinarianRepository;

    @Autowired
    public VeterinarianServiceImpl(VeterinarianRepository veterinarianRepository){
        this.veterinarianRepository = veterinarianRepository;
    }

    @Override
    public List<Veterinarian> findAllByClinicId(long clinicId) {
        return veterinarianRepository.findAllByClinicId(clinicId);
    }

    @Override
    public List<Veterinarian> findAll() {
        return veterinarianRepository.findAll();
    }

    @Override
    public Veterinarian findOne(long id) {
        return veterinarianRepository.findOne(id);
    }

    @Override
    public Veterinarian save(Veterinarian veterinarian) {
        return veterinarianRepository.save(veterinarian);
    }

    @Override
    public void delete(long id) {
        veterinarianRepository.delete(id);
    }
}
