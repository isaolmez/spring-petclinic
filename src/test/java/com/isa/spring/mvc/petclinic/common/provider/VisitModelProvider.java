package com.isa.spring.mvc.petclinic.common.provider;

import com.isa.spring.mvc.petclinic.data.model.Visit;

import java.util.Date;
import java.util.UUID;

public class VisitModelProvider implements TestModelProvider<Visit> {

    public static final VisitModelProvider INSTANCE = new VisitModelProvider();

    private final Date date = new Date();

    private VisitModelProvider() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
    }

    @Override
    public Visit randomModel() {
        Visit visit = new Visit();
        visit.setDescription("Test Description-" + UUID.randomUUID().toString());
        visit.setVisitDate(new Date());
        return visit;
    }

    @Override
    public Visit sameModel() {
        Visit visit = new Visit();
        visit.setDescription("Test Description");
        visit.setVisitDate(date);
        return visit;
    }
}
