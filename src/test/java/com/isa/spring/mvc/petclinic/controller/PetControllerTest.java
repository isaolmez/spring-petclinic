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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Transactional
public class PetControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldListPets() throws Exception {
        mockMvc.perform(get("/owners/1/pets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pets"));
    }

    @Test
    public void shouldAccess_Create_WithGet() throws Exception {
        mockMvc.perform(get("/owners/1/pets/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void shouldAccess_Create_WithPost() throws Exception {
        mockMvc.perform(post("/owners/1/pets/create")
                .param("name", "test name")
                .param("birthDate", "01-01-2000")
                .param("type", "bird"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1/pets"));
    }

    @Test
    public void shouldFail_Create_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/owners/1/pets/create")
                .param("name", ""))
                .andDo(print())
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Edit_WithGet() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void shouldAccess_Edit_WithPost() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .param("name", "test name")
                .param("birthDate", "01.01.2000")
                .param("type", "bird"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1/pets"));
    }

    @Test
    public void shouldFail_Edit_WithPost_WhenValidationFails() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .param("name", ""))
                .andDo(print())
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().errorCount(1));
    }

    @Test
    public void shouldAccess_Details_WithGet() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/details"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void shouldNotAccess_Details_WithPost() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/details"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldAccess_Delete_WithGet() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/delete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void shouldAccess_Delete_WithPost() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/delete"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1/pets"));

    }
}
