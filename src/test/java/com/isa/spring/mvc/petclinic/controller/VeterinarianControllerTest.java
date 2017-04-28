package com.isa.spring.mvc.petclinic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class VeterinarianControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldListVeterinarians() throws Exception {
        mockMvc.perform(get("/clinics/1/vets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("veterinarians"));
    }

    @Test
    public void shouldAccess_Create_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/vets/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("veterinarian"));
    }

    @Test
    public void shouldAccess_Create_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/create")
                .param("firstName", "test first name")
                .param("lastName", "test last name")
                .param("address.address", "test address")
                .param("contact.emailAddress", "test@test.com")
                .param("specialties", "surgery"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/vets"));
    }

    @Test
    public void shouldFail_Create_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/create")
                .param("firstName", "test first name"))
                .andDo(print())
                .andExpect(model().attributeHasErrors("veterinarian"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Edit_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/vets/1/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("veterinarian"));
    }

    @Test
    public void shouldAccess_Edit_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/1/edit")
                .param("firstName", "test first name")
                .param("lastName", "test last name")
                .param("address.address", "test address")
                .param("contact.emailAddress", "test@test.com")
                .param("specialties", "surgery"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/vets"));
    }

    @Test
    public void shouldFail_Edit_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/1/edit")
                .param("firstName", "test first name")
                .param("lastName", ""))
                .andDo(print())
                .andExpect(model().attributeHasErrors("veterinarian"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Details_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/vets/1/details"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("veterinarian"));
    }

    @Test
    public void shouldNotAccess_Details_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/1/details"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldAccess_Delete_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/vets/1/delete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("veterinarian"));
    }

    @Test
    public void shouldAccess_Delete_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/vets/1/delete"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/vets"));

    }
}
