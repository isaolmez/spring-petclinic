package com.isa.spring.mvc.petclinic.service.impl;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.repository.OwnerRepository;
import com.isa.spring.mvc.petclinic.service.OwnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findAllByClinicId(long clinicId) {
        return ownerRepository.findAllByClinicId(clinicId);
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner findOne(long id) {
        return ownerRepository.findOne(id);
    }

    @Override
    public Owner save(Owner clinic) {
        return ownerRepository.save(clinic);
    }

    @Override
    public void delete(long id) {
        ownerRepository.delete(id);
    }
}
