package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.validator.OwnerValidator;
import com.isa.spring.mvc.petclinic.service.ClinicService;
import com.isa.spring.mvc.petclinic.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clinics/{clinicId}/owners/{ownerId}")
public class OwnerController {
    private final OwnerService ownerService;

    private final ClinicService clinicService;

    @Autowired
    public OwnerController(OwnerService ownerService, ClinicService clinicService) {
        this.ownerService = ownerService;
        this.clinicService = clinicService;
    }

    @ModelAttribute("clinic")
    public Clinic getClinic(@PathVariable("clinicId") long clinicId) {
        return clinicService.findOne(clinicId);
    }

    @ModelAttribute("owner")
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return ownerService.findOne(ownerId);
    }

    @InitBinder("owner")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("owner")
    public void setValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(new OwnerValidator());
    }

    @GetMapping("/details")
    public String details() {
        return "owners/details";
    }

    @GetMapping("/edit")
    public String edit() {
        return "owners/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "owners/edit";
        } else {
            ownerService.save(owner);
            return "redirect:/clinics/{clinicId}/owners";
        }
    }

    @GetMapping("/delete")
    public String delete() {
        return "owners/delete";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable("ownerId") long ownerId) {
        ownerService.delete(ownerId);
        return "redirect:/clinics/{clinicId}/owners";
    }
}
