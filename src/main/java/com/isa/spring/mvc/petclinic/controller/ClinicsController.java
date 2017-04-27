package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.validator.ClinicValidator;
import com.isa.spring.mvc.petclinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clinics")
public class ClinicsController {

    private final ClinicService clinicService;

    @Autowired
    public ClinicsController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder
    public void setValidator(WebDataBinder dataBinder){
        dataBinder.setValidator(new ClinicValidator());
    }

    @GetMapping(path = {"", "/index"})
    public String list(ModelMap model) {
        final List<Clinic> allClinics = clinicService.findAll();
        model.addAttribute("clinics", allClinics);
        return "clinics/index";
    }

    @GetMapping("/create")
    public String create(ModelMap model) {
        Clinic clinic = new Clinic();
        model.addAttribute("clinic", clinic);
        return "clinics/create";
    }

    @PostMapping("/create")
    public String create(@Valid Clinic clinic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/clinics/create";
        } else {
            clinicService.save(clinic);
            return "redirect:/clinics";
        }
    }
}
