package com.isa.spring.mvc.petclinic.controller;

import org.junit.Before;
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
@WebAppConfiguration
@SpringBootTest
@Transactional
public class OwnerControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldListOwners() throws Exception {
        mockMvc.perform(get("/clinics/1/owners"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owners"));
    }

    @Test
    public void shouldAccess_Create_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/owners/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void shouldAccess_Create_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/create")
                .param("firstName", "test first name")
                .param("lastName", "test last name")
                .param("address.address", "test address")
                .param("contact.emailAddress", "test@test.com"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/owners"));
    }

    @Test
    public void shouldFail_Create_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/create")
                .param("firstName", "test first name"))
                .andDo(print())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Edit_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/owners/1/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void shouldAccess_Edit_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/1/edit")
                .param("firstName", "test first name")
                .param("lastName", "test last name")
                .param("address.address", "test address")
                .param("contact.emailAddress", "test@test.com"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/owners"));
    }

    @Test
    public void shouldFail_Edit_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/1/edit")
                .param("firstName", "test first name")
                .param("lastName", ""))
                .andDo(print())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Details_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/owners/1/details"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void shouldNotAccess_Details_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/1/details"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldAccess_Delete_WithGet() throws Exception {
        mockMvc.perform(get("/clinics/1/owners/1/delete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void shouldAccess_Delete_WithPost() throws Exception {
        mockMvc.perform(post("/clinics/1/owners/1/delete"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clinics/1/owners"));

    }
}
