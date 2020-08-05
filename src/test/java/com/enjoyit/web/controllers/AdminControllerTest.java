package com.enjoyit.web.controllers;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.enjoyit.services.EventService;
import com.enjoyit.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        doAnswer((answer) -> {
            return null;
        }).when(this.userService).ban("U0001");
        doThrow(EntityNotFoundException.class).when(this.userService).ban("U0002");

        doAnswer((answer) -> {
            return null;
        }).when(this.userService).authorize("U0001");
        doThrow(EntityNotFoundException.class).when(this.userService).authorize("U0002");

        doAnswer((answer) -> {
            return null;
        }).when(this.eventService).ban("E0001");
        doThrow(EntityNotFoundException.class).when(this.eventService).ban("E0002");
    }

    @Test
    public void returnExpectedStatusIfUserIsBannedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/users/ban/U0001")).andExpect(status().isNoContent());
    }

    @Test
    public void throwExpectedStatusIfUserIsNotBannedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/users/ban/U0002")).andExpect(status().isBadRequest());
    }


    @Test
    public void returnExpectedStatusIfUserIsAuthorizedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/users/authorize/U0001")).andExpect(status().isNoContent());
    }

    @Test
    public void throwExpectedStatusIfUserIsNotAuthorizedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/users/authorize/U0002")).andExpect(status().isBadRequest());
    }

    @Test
    public void returnExpectedStatusIfEventIsBannedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/events/ban/E0001")).andExpect(status().isNoContent());
    }

    @Test
    public void throwExpectedStatusIfEventIsBannedSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/events/ban/E0002")).andExpect(status().isBadRequest());
    }

}
