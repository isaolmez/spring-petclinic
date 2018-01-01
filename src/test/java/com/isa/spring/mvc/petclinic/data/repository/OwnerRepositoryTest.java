package com.isa.spring.mvc.petclinic.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.isa.spring.mvc.petclinic.common.provider.OwnerModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.PetModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.model.Pet;
import com.isa.spring.mvc.petclinic.data.model.PetType;
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
public class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    private Owner owner;

    private Clinic clinic;

    private PetType petType;

    @Before
    public void setUp() {
        this.owner = OwnerModelProvider.INSTANCE.randomModel();
        this.clinic = clinicRepository.findOne(1L);
        this.petType = petTypeRepository.findOne(1L);
        this.owner.setClinic(clinic);
    }

    @Test
    public void shouldInsert() {
        final long initialCount = ownerRepository.count();

        Owner saved = ownerRepository.save(owner);
        final long actualCount = ownerRepository.count();

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals(initialCount + 1, actualCount);
    }

    @Test
    public void shouldInsert_ThroughCascade() {
        final long initialCount = ownerRepository.count();

        /**
         * If insert cascade is enabled, flush operation traverses relations and persists them,
         * without explicit persist on individual unmanaged entities.
         */
        clinic.addOwner(owner);
        ownerRepository.flush();
        final long actualCount = ownerRepository.count();

        assertNotNull(owner);
        assertTrue(owner.getId() > 0);
        assertEquals(initialCount + 1, actualCount);
    }

    @Test
    public void shouldFetch() {
        Owner saved = ownerRepository.save(owner);

        Owner actual = ownerRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedFirstName = "Updated";
        Owner saved = ownerRepository.save(owner);
        Owner fetched = ownerRepository.findOne(saved.getId());

        fetched.setFirstName(expectedFirstName);
        Owner updated = ownerRepository.save(fetched);
        Owner actual = ownerRepository.findOne(updated.getId());

        assertNotNull(actual);
        assertEquals(expectedFirstName, actual.getFirstName());
    }

    @Test
    public void shouldDelete() {
        Owner saved = ownerRepository.save(owner);
        final long initialCount = ownerRepository.count();

        ownerRepository.delete(saved);
        final long actualCount = ownerRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldNotDelete_WhenEntityIsInParentCollection() {
        this.clinic.addOwner(owner);
        // Flushes here and inserts owner, since clinic is in persistence context
        final long initialCount = ownerRepository.count();

        ownerRepository.delete(owner.getId());
        final long actualCount = ownerRepository.count();

        assertEquals(initialCount, actualCount);
    }

    @Test
    public void shouldDelete_ThroughOrphanRemoval() {
        this.clinic.addOwner(owner);
        // Flushes here and inserts owner, since clinic is in persistence context
        // Constrast this with ClinicRepositoryTest.shouldDeleteOrphans()
        final long initialCount = ownerRepository.count();

        this.clinic.removeOwner(owner);
        // Flushes here and inserts owner, since clinic is in persistence context
        final long actualCount = ownerRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldCascade_Insert() {
        Pet initialPet = PetModelProvider.INSTANCE.randomModel();
        initialPet.setType(petType);
        owner.addPet(initialPet);

        Owner saved = ownerRepository.save(owner);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        saved.getPets().forEach(actualPet -> assertTrue(actualPet.getId() > 0));
    }

    @Test
    public void shouldCascade_Update() {
        final String expectedPetName = "Updated Pet";
        final String expectedOwnerName = "Updated Owner";
        Pet initialPet = PetModelProvider.INSTANCE.randomModel();
        initialPet.setType(petType);
        owner.addPet(initialPet);
        Owner saved = ownerRepository.save(owner);

        saved.setFirstName(expectedOwnerName);
        saved.getPets().forEach(pet -> pet.setName(expectedPetName));
        Owner updated = ownerRepository.save(saved);
        Owner actual = ownerRepository.findOne(updated.getId());

        assertEquals(expectedOwnerName, actual.getFirstName());
        actual.getPets().forEach(pet -> assertEquals(expectedPetName, pet.getName()));

    }

    @Test
    public void shouldCascade_Delete() {
        Pet initialPet = PetModelProvider.INSTANCE.randomModel();
        initialPet.setType(petType);
        owner.addPet(initialPet);
        Owner saved = ownerRepository.save(owner);

        ownerRepository.delete(saved.getId());

        Owner actualOwner = ownerRepository.findOne(saved.getId());
        assertNull(actualOwner);
        Pet actualPet = petRepository.findOne(initialPet.getId());
        assertNull(actualPet);
    }

    @Test
    public void shouldDeleteOrphans() {
        Pet initialPet = PetModelProvider.INSTANCE.randomModel();
        initialPet.setType(petType);
        owner.addPet(initialPet);
        Owner saved = ownerRepository.save(owner);

        // Just make the relation entity orphan, do not delete the parent entity
        saved.removePet(initialPet);
        ownerRepository.flush();

        Pet actualPet = petRepository.findOne(initialPet.getId());
        assertNull(actualPet);
    }
}
