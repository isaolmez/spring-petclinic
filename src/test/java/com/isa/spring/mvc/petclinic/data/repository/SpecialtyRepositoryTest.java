package com.isa.spring.mvc.petclinic.data.repository;

import com.google.common.collect.Sets;
import com.isa.spring.mvc.petclinic.common.provider.PetTypeModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.SpecialtyModelProvider;
import com.isa.spring.mvc.petclinic.data.model.PetType;
import com.isa.spring.mvc.petclinic.data.model.Specialty;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpecialtyRepositoryTest {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    private Specialty specialty;

    @Before
    public void setUp() {
        this.specialty = SpecialtyModelProvider.INSTANCE.randomModel();
        this.specialty.setVeterinarians(Sets.newHashSet(veterinarianRepository.findOne(1L)));
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
        Specialty saved = specialtyRepository.save(specialty);
        final long initialCount = specialtyRepository.count();

        saved.removeVeterinarians();
        specialtyRepository.delete(saved.getId());
        final long actualCount = specialtyRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }
}
