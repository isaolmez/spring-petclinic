package com.isa.spring.mvc.petclinic.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.isa.spring.mvc.petclinic.common.provider.PetTypeModelProvider;
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
public class PetTypeRepositoryTest {

    @Autowired
    private PetTypeRepository petTypeRepository;

    private PetType petType;

    @Before
    public void setUp(){
        this.petType = PetTypeModelProvider.INSTANCE.randomModel();
    }

    @Test
    public void shouldInsert() {
        PetType saved = petTypeRepository.save(petType);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    @Test
    public void shouldFetch(){
        PetType saved = petTypeRepository.save(petType);

        PetType actual = petTypeRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate(){
        final String expectedName = "Updated";
        PetType saved = petTypeRepository.save(petType);
        PetType fetched = petTypeRepository.findOne(saved.getId());

        fetched.setName(expectedName);
        PetType updated = petTypeRepository.save(fetched);
        PetType actual = petTypeRepository.findOne(updated.getId());

        assertEquals(expectedName, actual.getName());
    }

    @Test
    public void shouldDelete(){
        PetType saved = petTypeRepository.save(petType);
        final long initialCount = petTypeRepository.count();

        petTypeRepository.delete(saved.getId());
        final long actualCount = petTypeRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }
}
