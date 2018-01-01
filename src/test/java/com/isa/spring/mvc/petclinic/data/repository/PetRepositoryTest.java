package com.isa.spring.mvc.petclinic.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.isa.spring.mvc.petclinic.common.provider.PetModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.VisitModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.model.Pet;
import com.isa.spring.mvc.petclinic.data.model.PetType;
import com.isa.spring.mvc.petclinic.data.model.Visit;
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
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VisitRepository visitRepository;

    private Pet pet;

    private Owner owner;

    private PetType petType;

    @Before
    public void setUp() {
        this.pet = PetModelProvider.INSTANCE.randomModel();
        this.owner = ownerRepository.findOne(1L);
        this.petType = petTypeRepository.findOne(1L);
        this.pet.setOwner(this.owner);
        this.pet.setType(this.petType);
    }

    @Test
    public void shouldInsert() {
        Pet saved = petRepository.save(pet);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    @Test
    public void shouldInsert_ThroughCascade() {
        this.owner.addPet(this.pet);

        petRepository.flush();

        assertNotNull(pet);
        assertTrue(pet.getId() > 0);
    }

    @Test
    public void shouldFetch() {
        Pet saved = petRepository.save(pet);

        Pet actual = petRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedName = "Updated";
        Pet saved = petRepository.save(pet);
        Pet fetched = petRepository.findOne(saved.getId());

        fetched.setName(expectedName);
        Pet updated = petRepository.save(fetched);
        Pet actual = petRepository.findOne(updated.getId());

        assertNotNull(actual);
        assertEquals(expectedName, actual.getName());
    }

    @Test
    public void shouldDelete() {
        Pet saved = petRepository.save(pet);
        final long initialCount = petRepository.count();

        petRepository.delete(saved.getId());
        final long actualCount = petRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldDelete_ThroughOrphanRemoval() {
        this.owner.addPet(pet);
        final long initialCount = petRepository.count();

        this.owner.removePet(pet);
        petRepository.flush();
        final long actualCount = petRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }

    @Test
    public void shouldCascade_Insert() {
        final Visit initialVisit = VisitModelProvider.INSTANCE.randomModel();
        pet.addVisit(initialVisit);

        Pet savedPet = petRepository.save(pet);

        assertNotNull(savedPet);
        assertTrue(savedPet.getId() > 0);
        savedPet.getVisits().forEach(actualVisit -> assertTrue(actualVisit.getId() > 0));
    }

    @Test
    public void shouldCascade_Update() {
        final String expectedPetName = "Updated Pet";
        final String expectedVisitDesc = "Updated Visit";
        final Visit initialVisit = VisitModelProvider.INSTANCE.randomModel();
        pet.addVisit(initialVisit);
        Pet savedPet = petRepository.save(pet);

        savedPet.setName(expectedPetName);
        savedPet.getVisits().forEach(visit -> visit.setDescription(expectedVisitDesc));
        Pet actualPet = petRepository.save(savedPet);

        assertEquals(expectedPetName, actualPet.getName());
        actualPet.getVisits().forEach(visit -> assertEquals(expectedVisitDesc, visit.getDescription()));
    }

    @Test
    public void shouldCascade_Delete() {
        final Visit initialVisit = VisitModelProvider.INSTANCE.randomModel();
        pet.addVisit(initialVisit);

        Pet savedPet = petRepository.save(pet);
        petRepository.delete(savedPet.getId());

        Pet actualPet = petRepository.findOne(savedPet.getId());
        assertNull(actualPet);
        Visit actualVisit = visitRepository.findOne(initialVisit.getId());
        assertNull(actualVisit);
    }

    @Test
    public void shouldDeleteOrphans() {
        final Visit initialVisit = VisitModelProvider.INSTANCE.randomModel();
        pet.addVisit(initialVisit);
        Pet savedPet = petRepository.save(pet);

        savedPet.removeVisit(initialVisit);
        petRepository.flush();

        Visit actualVisit = visitRepository.findOne(initialVisit.getId());
        assertNull(actualVisit);
    }
}
