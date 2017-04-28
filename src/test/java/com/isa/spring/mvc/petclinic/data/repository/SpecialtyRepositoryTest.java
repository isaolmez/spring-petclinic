package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.common.provider.SpecialtyModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.VeterinarianModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Specialty;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpecialtyRepositoryTest {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    private Specialty specialty;

    @Before
    public void setUp() {
        this.specialty = SpecialtyModelProvider.INSTANCE.randomModel();
    }

    @Test
    public void shouldInsert() {
        Specialty saved = specialtyRepository.save(specialty);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    @Test
    public void shouldFetch() {
        Specialty saved = specialtyRepository.save(specialty);

        Specialty actual = specialtyRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedName = "Updated";
        Specialty saved = specialtyRepository.save(specialty);
        Specialty fetched = specialtyRepository.findOne(saved.getId());

        fetched.setName(expectedName);
        Specialty updated = specialtyRepository.save(fetched);
        Specialty actual = specialtyRepository.findOne(updated.getId());

        assertEquals(expectedName, actual.getName());
    }

    @Test
    public void shouldDelete() {
        final Veterinarian veterinarian = veterinarianRepository.findOne(1L);
        specialty.addVeterinarian(veterinarian);
        Specialty saved = specialtyRepository.save(specialty);
        final long initialCount = specialtyRepository.count();

        saved.removeVeterinarians();
        specialtyRepository.delete(saved.getId());
        final long actualCount = specialtyRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldCascase_Insert() {
        final Veterinarian initialVeterinarian = VeterinarianModelProvider.INSTANCE.randomModel();
        initialVeterinarian.setClinic(clinicRepository.findOne(1L));
        specialty.addVeterinarian(initialVeterinarian);

        Specialty savedSpecialty = specialtyRepository.save(specialty);

        assertNotNull(savedSpecialty);
        assertTrue(savedSpecialty.getId() > 0);
        savedSpecialty.getVeterinarians().forEach(veterinarian -> assertTrue(veterinarian.getId() > 0));
    }

    @Test
    public void shouldCascase_Update() {
        final String expectedVetName = "Updated Vet";
        final String expectedSpecialtyName = "Updated Specialty";
        final Veterinarian initialVeterinarian = VeterinarianModelProvider.INSTANCE.randomModel();
        initialVeterinarian.setClinic(clinicRepository.findOne(1L));
        specialty.addVeterinarian(initialVeterinarian);
        Specialty savedSpecialty = specialtyRepository.save(specialty);

        savedSpecialty.setName(expectedSpecialtyName);
        savedSpecialty.getVeterinarians().forEach(vet->vet.setFirstName(expectedVetName));
        Specialty updatedSpecialty = specialtyRepository.save(savedSpecialty);
        Specialty actualSpecialty = specialtyRepository.findOne(updatedSpecialty.getId());

        assertEquals(expectedSpecialtyName, actualSpecialty.getName());
        actualSpecialty.getVeterinarians().forEach(actualVet -> assertEquals(expectedVetName, actualVet.getFirstName()));
    }
}
