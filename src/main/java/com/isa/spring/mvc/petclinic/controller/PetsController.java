package com.isa.spring.mvc.petclinic.controller;

import com.isa.spring.mvc.petclinic.data.model.Owner;
import com.isa.spring.mvc.petclinic.data.model.Pet;
import com.isa.spring.mvc.petclinic.data.model.PetType;
import com.isa.spring.mvc.petclinic.data.repository.PetRepository;
import com.isa.spring.mvc.petclinic.data.repository.PetTypeRepository;
import com.isa.spring.mvc.petclinic.service.OwnerService;
import com.isa.spring.mvc.petclinic.data.formatter.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetsController {
    private final PetRepository petRepository;

    private final PetTypeRepository petTypeRepository;

    private final OwnerService ownerService;

    @Autowired
    public PetsController(PetRepository petRepository, OwnerService ownerService, PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.ownerService = ownerService;
        this.petTypeRepository = petTypeRepository;
    }

    @InitBinder("pet")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder
    public void setFormatters(WebDataBinder webDataBinder) {
        webDataBinder.addCustomFormatter(new DateFormatter("dd-MM-yyyy"));
    }

    @ModelAttribute("owner")
    public Owner getOwner(@PathVariable("ownerId") long ownerId) {
        return ownerService.findOne(ownerId);
    }

    @ModelAttribute("types")
    public Collection<PetType> getTypes(){
        return petTypeRepository.findAll();
    }

    @GetMapping(path = {"", "/index"})
    public String list(@PathVariable("ownerId") long ownerId, ModelMap model) {
        List<Pet> pets = petRepository.findAllByOwnerId(ownerId);
        model.addAttribute("pets", pets);
        return "pets/index";
    }

    @GetMapping("/create")
    public String create(ModelMap model) {
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        return "pets/create";
    }

    @PostMapping("/create")
    public String create(@Valid Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pets/create";
        } else {
            petRepository.save(pet);
            return "redirect:/owners/{ownerId}/pets";
        }
    }

}
