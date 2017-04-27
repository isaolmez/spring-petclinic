package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Veterinarian;
import com.isa.spring.mvc.petclinic.data.repository.ClinicRepository;
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
@RequestMapping("/clinics/{clinicId}/vets/{vetId}")
public class VeterinarianController {
    private final VeterinarianService veterinarianService;

    private final ClinicRepository clinicRepository;

    @Autowired
    public VeterinarianController(VeterinarianService veterinarianService, ClinicRepository clinicRepository) {
        this.veterinarianService = veterinarianService;
        this.clinicRepository = clinicRepository;
    }

    @ModelAttribute("clinic")
    public Clinic getClinic(@PathVariable("clinicId") long clinicId) {
        return clinicRepository.findOne(clinicId);
    }

    @ModelAttribute("veterinarian")
    public Veterinarian getVeterinarian(@PathVariable("vetId") long vetId) {
        return veterinarianService.findOne(vetId);
    }

    @InitBinder("veterinarian")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("veterinarian")
    public void setValidator(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new VeterinarianValidator());
    }

    @GetMapping("/details")
    public String details() {
        return "veterinarians/details";
    }

    @GetMapping("/edit")
    public String edit() {
        return "veterinarians/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Veterinarian veterinarian, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "veterinarians/edit";
        } else {
            veterinarianService.save(veterinarian);
            return "redirect:/clinics/{clinicId}/vets";
        }
    }

    @GetMapping("/delete")
    public String delete() {
        return "veterinarians/delete";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable("vetId") long vetId) {
        veterinarianService.delete(vetId);
        return "redirect:/clinics/{clinicId}/vets";
    }
}
