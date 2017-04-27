package com.isa.spring.mvc.petclinic.data.repository;

import com.isa.spring.mvc.petclinic.common.provider.VeterinarianModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.VisitModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import com.isa.spring.mvc.petclinic.data.model.Visit;
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
public class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PetRepository petRepository;

    private Visit visit;

    @Before
    public void setUp() {
        this.visit = VisitModelProvider.INSTANCE.randomModel();
        this.visit.setPet(petRepository.findOne(1L));
    }

    @Test
    public void shouldInsert() {
        Visit saved = visitRepository.save(visit);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    @Test
    public void shouldFetch() {
        Visit saved = visitRepository.save(visit);

        Visit actual = visitRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedDescription = "Updated";
        Visit saved = visitRepository.save(visit);
        Visit fetched = visitRepository.findOne(saved.getId());

        fetched.setDescription(expectedDescription);
        Visit updated = visitRepository.save(fetched);
        Visit actual = visitRepository.findOne(updated.getId());

        assertEquals(expectedDescription, actual.getDescription());
    }

    @Test
    public void shouldDelete() {
        Visit saved = visitRepository.save(visit);
        final long initialCount = visitRepository.count();

        visitRepository.delete(saved.getId());
        final long actualCount = visitRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }
}
