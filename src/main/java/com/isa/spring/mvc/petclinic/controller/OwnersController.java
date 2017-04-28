package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.validator.OwnerValidator;
import com.isa.spring.mvc.petclinic.service.ClinicService;
import com.isa.spring.mvc.petclinic.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clinics/{clinicId}/owners")
public class OwnersController {
    private final OwnerService ownerService;

    private final ClinicService clinicService;

    @Autowired
    public OwnersController(OwnerService ownerService, ClinicService clinicService) {
        this.ownerService = ownerService;
        this.clinicService = clinicService;
    }

    @ModelAttribute("clinic")
    public Clinic getClinic(@PathVariable("clinicId") long clinicId) {
        return clinicService.findOne(clinicId);
    }

    @InitBinder("owner")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("owner")
    public void setValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(new OwnerValidator());
    }

    @GetMapping(path = {"", "/index"})
    public String list(@PathVariable("clinicId") long clinicId, ModelMap model) {
        List<Owner> owners = ownerService.findAllByClinicId(clinicId);
        model.addAttribute("owners", owners);
        return "owners/index";
    }

    @GetMapping("/create")
    public String create(ModelMap model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owners/create";
    }

    @PostMapping("/create")
    public String create(@Valid Owner owner, BindingResult bindingResult, @ModelAttribute(binding = false) Clinic clinic) {
        if (bindingResult.hasErrors()) {
            return "owners/create";
        } else {
            owner.setClinic(clinic);
            ownerService.save(owner);
            return "redirect:/clinics/{clinicId}/owners";
        }
    }
}
