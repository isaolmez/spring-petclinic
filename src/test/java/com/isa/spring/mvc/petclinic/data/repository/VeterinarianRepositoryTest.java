package com.isa.spring.mvc.petclinic.data.repository;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.isa.spring.mvc.petclinic.common.provider.SpecialtyModelProvider;
import com.isa.spring.mvc.petclinic.common.provider.VeterinarianModelProvider;
import com.isa.spring.mvc.petclinic.data.model.Specialty;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VeterinarianRepositoryTest {

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private Veterinarian veterinarian;

    @Before
    public void setUp() {
        this.veterinarian = VeterinarianModelProvider.INSTANCE.randomModel();
        this.veterinarian.setClinic(clinicRepository.findOne(1L));
        this.veterinarian.setSpecialties(Sets.newHashSet(specialtyRepository.findOne(29L)));
    }

    @Test
    public void shouldInsert() {
        Veterinarian saved = veterinarianRepository.save(veterinarian);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    @Test
    public void shouldFetch() {
        Veterinarian saved = veterinarianRepository.save(veterinarian);

        Veterinarian actual = veterinarianRepository.findOne(saved.getId());

        assertNotNull(actual);
        assertEquals(saved.getId(), actual.getId());
    }

    @Test
    public void shouldUpdate() {
        final String expectedName = "Updated";
        Veterinarian saved = veterinarianRepository.save(veterinarian);
        Veterinarian fetched = veterinarianRepository.findOne(saved.getId());

        fetched.setFirstName(expectedName);
        Veterinarian updated = veterinarianRepository.save(fetched);
        Veterinarian actual = veterinarianRepository.findOne(updated.getId());

        assertEquals(expectedName, actual.getFirstName());
    }

    @Test
    public void shouldDelete() {
        Veterinarian saved = veterinarianRepository.save(veterinarian);
        final long initialCount = veterinarianRepository.count();

        veterinarianRepository.delete(saved.getId());
        final long actualCount = veterinarianRepository.count();

        assertEquals(initialCount - 1, actualCount);
    }
}
