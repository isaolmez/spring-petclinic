package com.isa.spring.mvc.petclinic.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.isa.spring.mvc.petclinic.common.provider.ClinicModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.OwnerModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.VeterinarianModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClinicRepositoryTest {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    private Clinic clinic;

    @Before
    public void setUp() {
        this.clinic = ClinicModelProvider.INSTANCE.randomModel();
    }

    @Test
    public void shouldInsert() {
        final long initialCount = clinicRepository.count();

        Clinic actual = clinicRepository.save(clinic);
        final long actualCount = clinicRepository.count();

        assertNotNull(actual);
        assertTrue(actual.getId() > 0);
        assertEquals(initialCount + 1, actualCount);
    }

    @Test
    public void shouldFetch() {
        Clinic saved = clinicRepository.save(clinic);

        Clinic actual = clinicRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedName = "Test";
        Clinic saved = clinicRepository.save(clinic);
        Clinic fetched = clinicRepository.findOne(saved.getId());

        fetched.setName(expectedName);
        Clinic updated = clinicRepository.save(fetched);
        Clinic actual = clinicRepository.findOne(updated.getId());

        assertNotNull(actual);
        assertEquals(expectedName, actual.getName());
    }

    @Test
    public void shouldDelete() {
        Clinic saved = clinicRepository.save(clinic);
        final long initialCount = clinicRepository.count();

        clinicRepository.delete(saved);
        final long actualCount = clinicRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldCascade_Insert() {
        clinic.addOwner(OwnerModelProvider.INSTANCE.randomModel());
        clinic.addVeterinarian(VeterinarianModelProvider.INSTANCE.randomModel());

        Clinic actualClinic = clinicRepository.save(clinic);

        assertNotNull(actualClinic);
        assertTrue(actualClinic.getId() > 0);
        assertTrue(actualClinic.getOwners().size() > 0);
        actualClinic.getOwners().forEach(owner -> assertTrue(owner.getId() > 0));
        actualClinic.getVeterinarians().forEach(vet -> assertTrue(vet.getId() > 0));
    }

    @Test
    public void shouldCascade_Update() {
        final String expectedOwnerName = "Updated Owner";
        final String expectedVetName = "Updated Vet";
        final String expectedClinicName = "Updated Clinic";
        clinic.addOwner(OwnerModelProvider.INSTANCE.randomModel());
        clinic.addVeterinarian(VeterinarianModelProvider.INSTANCE.randomModel());
        Clinic savedClinic = clinicRepository.save(clinic);
        Clinic updatedClinic = clinicRepository.findOne(savedClinic.getId());
        updatedClinic.setName(expectedClinicName);
        updatedClinic.getVeterinarians().forEach(vet -> vet.setFirstName(expectedVetName));
        updatedClinic.getOwners().forEach(owner -> owner.setFirstName(expectedOwnerName));

        // Flushes here or alternatively
        // clinicRepository.save(clinic);
        Clinic actualClinic = clinicRepository.findOne(updatedClinic.getId());

        assertEquals(expectedClinicName, actualClinic.getName());
        actualClinic.getVeterinarians().forEach(vet -> assertEquals(expectedVetName, vet.getFirstName()));
        actualClinic.getOwners().forEach(owner -> assertEquals(expectedOwnerName, owner.getFirstName()));
    }

    @Test
    public void shouldCascade_Delete() {
        final Owner initialOwner = OwnerModelProvider.INSTANCE.randomModel();
        final Veterinarian initialVeterinarian = VeterinarianModelProvider.INSTANCE.randomModel();
        clinic.addOwner(initialOwner);
        clinic.addVeterinarian(initialVeterinarian);
        Clinic initialClinic = clinicRepository.save(clinic);

        clinicRepository.delete(initialClinic);

        Clinic actualClinic = clinicRepository.findOne(initialClinic.getId());
        assertNull(actualClinic);
        Owner actualOwner = ownerRepository.findOne(initialOwner.getId());
        assertNull(actualOwner);
        Veterinarian actualVeterinarian = veterinarianRepository.findOne(initialVeterinarian.getId());
        assertNull(actualVeterinarian);
    }

    @Test
    public void shouldDeleteOrphans() {
        final Owner initialOwner = OwnerModelProvider.INSTANCE.randomModel();
        final Veterinarian initialVeterinarian = VeterinarianModelProvider.INSTANCE.randomModel();
        clinic.addOwner(initialOwner);
        clinic.addVeterinarian(initialVeterinarian);
        Clinic savedClinic = clinicRepository.save(clinic);

        savedClinic.removeVeterinarian(initialVeterinarian);
        savedClinic.removeOwner(initialOwner);
        clinicRepository.flush();

        Owner actualOwner = ownerRepository.findOne(initialOwner.getId());
        assertNull(actualOwner);
        Veterinarian actualVeterinarian = veterinarianRepository.findOne(initialVeterinarian.getId());
        assertNull(actualVeterinarian);
    }
}
