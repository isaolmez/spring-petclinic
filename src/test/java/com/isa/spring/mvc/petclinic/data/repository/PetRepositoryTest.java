package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.common.provider.PetModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Pet;
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
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private Pet pet;

    @Before
    public void setUp() {
        this.pet = PetModelProvider.INSTANCE.randomModel();
        this.pet.setOwner(ownerRepository.findOne(1L));
        this.pet.setType(petTypeRepository.findOne(1L));
    }

    @Test
    public void shouldInsert() {
        Pet saved = petRepository.save(pet);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
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
}
