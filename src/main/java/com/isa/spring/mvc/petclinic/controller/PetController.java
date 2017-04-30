package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.formatter.DateFormatter;
import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.model.Pet;
import com.isa.spring.mvc.petclinic.data.model.PetType;
import com.isa.spring.mvc.petclinic.data.repository.PetRepository;
import com.isa.spring.mvc.petclinic.data.repository.PetTypeRepository;
import com.isa.spring.mvc.petclinic.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class PetController {
    private final PetRepository petRepository;

    private final OwnerService ownerService;

    private final PetTypeRepository petTypeRepository;

    @Autowired
    public PetController(PetRepository petRepository,
                         OwnerService ownerService,
                         PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.ownerService = ownerService;
        this.petTypeRepository = petTypeRepository;
    }

    @InitBinder("pet")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void setFormatters(WebDataBinder webDataBinder) {
        webDataBinder.addCustomFormatter(new DateFormatter("dd.MM.yyyy"));
    }

    @ModelAttribute("owner")
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return ownerService.findOne(ownerId);
    }

    @ModelAttribute("pet")
    public Pet getPet(@PathVariable("petId") long petId) {
        return petRepository.findOne(petId);
    }

    @ModelAttribute("types")
    public List<PetType> getTypes(){
        return petTypeRepository.findAll();
    }

    @GetMapping("details")
    public String details() {
        return "pets/details";
    }

    @GetMapping("/edit")
    public String edit() {
        return "pets/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pets/edit";
        } else {
            petRepository.save(pet);
            return "redirect:/owners/{ownerId}/pets";
        }
    }

    @GetMapping("/delete")
    public String delete() {
        return "pets/delete";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable("petId") long petId) {
        petRepository.delete(petId);
        return "redirect:/owners/{ownerId}/pets";
    }

}
