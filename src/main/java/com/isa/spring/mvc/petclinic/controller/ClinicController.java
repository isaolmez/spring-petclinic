package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Clinic;
import com.isa.spring.mvc.petclinic.data.validator.ClinicValidator;
import com.isa.spring.mvc.petclinic.service.ClinicService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinics/{clinicId}")
public class ClinicController {

    private final ClinicService clinicService;

    @Autowired
    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @ModelAttribute("clinic")
    public Clinic getClinic(@PathVariable("clinicId") long clinicId) {
        return clinicService.findOne(clinicId);
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder
    public void setValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(new ClinicValidator());
    }

    @GetMapping("/details")
    public String details() {
        return "clinics/details";
    }

    @GetMapping("/edit")
    public String edit() {
        return "clinics/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Clinic clinic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "clinics/edit";
        } else {
            clinicService.save(clinic);
            return "redirect:/clinics";
        }
    }

    @GetMapping("/delete")
    public String delete() {
        return "clinics/delete";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable("clinicId") long clinicId) {
        clinicService.delete(clinicId);
        return "redirect:/clinics";
    }
}
