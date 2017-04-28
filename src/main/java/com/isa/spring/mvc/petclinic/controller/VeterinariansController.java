package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Specialty;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import com.isa.spring.mvc.petclinic.data.repository.ClinicRepository;
import com.isa.spring.mvc.petclinic.data.repository.SpecialtyRepository;
import com.isa.spring.mvc.petclinic.data.validator.VeterinarianValidator;
import com.isa.spring.mvc.petclinic.service.VeterinarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clinics/{clinicId}/vets")
public class VeterinariansController {
    private final VeterinarianService veterinarianService;

    private final ClinicRepository clinicRepository;

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public VeterinariansController(VeterinarianService veterinarianService,
                                   ClinicRepository clinicRepository,
                                   SpecialtyRepository specialtyRepository) {
        this.veterinarianService = veterinarianService;
        this.clinicRepository = clinicRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @ModelAttribute("clinic")
    public Clinic getClinic(@PathVariable("clinicId") long clinicId) {
        return clinicRepository.findOne(clinicId);
    }

    @ModelAttribute("specialties")
    public List<Specialty> getSpecialties(){
        return specialtyRepository.findAll();
    }

    @InitBinder("veterinarian")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("veterinarian")
    public void setValidator(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new VeterinarianValidator());
    }

    @GetMapping(path = {"", "/index"})
    public String list(@PathVariable("clinicId") long clinicId, ModelMap model) {
        List<Veterinarian> veterinarians = veterinarianService.findAllByClinicId(clinicId);
        model.addAttribute("veterinarians", veterinarians);
        return "veterinarians/index";
    }

    @GetMapping("/create")
    public String create(ModelMap model) {
        Veterinarian veterinarian = new Veterinarian();
        model.addAttribute("veterinarian", veterinarian);
        return "veterinarians/create";
    }

    @PostMapping("/create")
    public String create(@Valid Veterinarian veterinarian,
                         BindingResult bindingResult,
                         @ModelAttribute(binding = false) Clinic clinic) {
        if (bindingResult.hasErrors()) {
            return "veterinarians/create";
        } else {
            veterinarian.setClinic(clinic);
            veterinarianService.save(veterinarian);
            return "redirect:/clinics/{clinicId}/vets";
        }
    }
}
