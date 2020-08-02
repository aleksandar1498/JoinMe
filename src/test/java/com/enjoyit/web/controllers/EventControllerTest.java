package com.enjoyit.web.controllers;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

import com.enjoyit.domain.dto.BaseEventDTO;
import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.enums.EventCategory;
import com.enjoyit.services.EventService;
import com.enjoyit.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EventService eventService;

    private static final BaseEventDTO VALID_EVENT = new BaseEventDTO("new title",new LocationDTO(),"no Description", LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(24), EventCategory.ART_EVENT);
    private static final EventDTO UPDATED = new EventDTO("new title",new LocationDTO(),"no Description", EventCategory.ART_EVENT);

    @BeforeEach
    public void setUp() {
        doAnswer((answer) ->{
            return null;
        }).when(this.eventService).cancelEventById("E0001");
        doThrow(EntityNotFoundException.class).when(this.eventService).cancelEventById("E0002");

        when(this.eventService.getAllEvents()).thenReturn(List.of(
                new EventDTO("No Title",new LocationDTO(),"No Description",EventCategory.ART_EVENT),
                new EventDTO("No Title",new LocationDTO(),"No Description",EventCategory.ART_EVENT),
                new EventDTO("No Title",new LocationDTO(),"No Description",EventCategory.ART_EVENT)
                ));
    }
    @Test
    void cancelEventMustThrowExpectedWhenEventIsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/E0001")).andExpect(status().isNoContent());
    }

    @Test
    void cancelEventMustReturnExpectedStatusIfEventIsSuccessfullyDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/E0002")).andExpect(status().isBadRequest());
    }

    @Test
    void editEventByIdMustThrowExpecteIfAnEventWithThatIdIsNotFound() throws Exception {
        when(this.eventService.editEventById("invalid", new BaseEventDTO())).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/events/invalid"))
        .andExpect(status().isBadRequest());
    }

    //    @Test
    //    void editEventByIdMustPerformChangesIfEventIsFound() throws Exception {
    //        when(this.eventService.editEventById("E0001", VALID_EVENT)).thenReturn(UPDATED);
    //        this.mockMvc.perform(MockMvcRequestBuilders
    //                .put("/events/E0001")
    //                .contentType(MediaType.APPLICATION_JSON)
    //                .content(asJsonString(VALID_EVENT)))
    //        .andExpect(status().isCreated())
    //        .andExpect(jsonPath("$.title").value("new title"));
    //    }

    @Test
    void getAllEventsMustReturnEmptyIfThereAreNotElements() throws Exception {
        when(this.eventService.getAllEvents()).thenReturn(new ArrayList<EventDTO>());
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
        .andExpect(status().isOk());

    }

    @Test
    void getAllEventsMustReturnTheEventsPresent() throws Exception {
        when(this.eventService.getAllEvents()).thenReturn(List.of(
                new EventDTO("No Title",new LocationDTO(),"No Description",EventCategory.ART_EVENT),
                new EventDTO("No Title",new LocationDTO(),"No Descrition",EventCategory.ART_EVENT),
                new EventDTO("No Title",new LocationDTO(),"No Description",EventCategory.ART_EVENT)
                ));
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
        .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
    //
    //    @Test
    //    void getAllEventsByOwnerShouldReturnEmptyIfThePrincipalDoesNotHaveEvents() throws Exception {
    //        when(this.eventService.getEventByOwner(principal.getName())).thenReturn(new ArrayList<EventDTO>());
    //        mockMvc.perform(MockMvcRequestBuilders.get("/events/user"))
    //        .andExpect(status().isOk())
    //        .andExpect(jsonPath("$.length()").value(0));
    //    }
    //
    //    @Test
    //    void getAllEventsByOwnerShouldReturnNotEmptyIfThePrincipalHasEvents() throws Exception {
    //        System.out.println(principal.getName());
    //        when(this.eventService.getEventByOwner(principal.getName())).thenReturn(List.of(
    //                new EventDTO(),
    //                new EventDTO()
    //                ));
    //        System.out.println(this.eventService.getEventByOwner(principal.getName()));
    //        mockMvc.perform(MockMvcRequestBuilders.get("/events/user"))
    //        .andExpect(status().isOk())
    //        .andExpect(jsonPath("$.length()").value(2));
    //    }

}
