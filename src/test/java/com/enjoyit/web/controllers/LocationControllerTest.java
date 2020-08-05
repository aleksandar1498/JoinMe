package com.enjoyit.web.controllers;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.enjoyit.domain.dto.LocationDTO;
import com.enjoyit.enums.LocationCategory;
import com.enjoyit.services.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class LocationControllerTest {
    private static final LocationDTO UPDATED_VALID_LOCATION = new LocationDTO("L0001", "Targovishte", "Teatros",
            LocationCategory.MOVIE_THEATER);
    private static final LocationDTO NEW_VALID_LOCATION = new LocationDTO("L0003", "Targovishte", "Teatros",
            LocationCategory.MOVIE_THEATER);
    private static final LocationDTO INVALID_LOCATION = new LocationDTO("L0002");

    private static List<LocationDTO> locations = new ArrayList<LocationDTO>() {
        {
            add(new LocationDTO("L0001", "Sofia", "PointBar", LocationCategory.BAR));
            add(new LocationDTO("L0002", "Varna", "HappyBar", LocationCategory.BAR));
        }
    };

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        when(locationService.findAllLocations()).thenReturn(locations);
        when(locationService.findLocationById("L0001")).thenReturn(Optional.of(locations.get(0)));
        when(locationService.update(ArgumentMatchers.anyString(),
                ArgumentMatchers.argThat(new LocationDTOArgumentMatcher(UPDATED_VALID_LOCATION))))
        .thenReturn(UPDATED_VALID_LOCATION);
        when(locationService.update(ArgumentMatchers.eq("L0002"),
                ArgumentMatchers.argThat(new LocationDTOArgumentMatcher(INVALID_LOCATION))))
        .thenThrow(new ConstraintViolationException(getConstraintViolations(INVALID_LOCATION)));
        doAnswer((answer) -> {
            return null;
        }).when(this.locationService).delete("L0001");
        doThrow(EntityNotFoundException.class).when(this.locationService).delete("L0004");
    }

    @Test
    void deleteLocationMustReturnExpectedStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/locations/{id}", "L0001"))
        .andExpect(status().isNoContent());
    }

    @Test
    void deleteLocationMustThrowExpectedIfIdIsNotPresent() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/locations/{id}", "L0004"))
        .andExpect(status().isBadRequest());
        // .andExpect(jsonPath("$.error",Matchers.containsStringIgnoringCase("Location
        // with this id does not exists")));
    }

    @Test
    void createLocationWithInvalidDataShouldThrowExpected() throws Exception {
        when(locationService.create(ArgumentMatchers.argThat(new LocationDTOArgumentMatcher(INVALID_LOCATION))))
        .thenThrow(new ConstraintViolationException(getConstraintViolations(INVALID_LOCATION)));

        mockMvc.perform(MockMvcRequestBuilders.post("/locations").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(INVALID_LOCATION))).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name").value("*Name cannot be empty"))
        .andExpect(jsonPath("$.city").value("*City cannot be empty"))
        .andExpect(jsonPath("$.locationCategory").value("*Location Category cannot be not set"));
    }

    @Test
    void createLocationWithValidDataShouldBeSuccessful() throws Exception {
        when(locationService.create(ArgumentMatchers.argThat(new LocationDTOArgumentMatcher(NEW_VALID_LOCATION))))
        .thenReturn(NEW_VALID_LOCATION);

        System.out.println(INVALID_LOCATION.getId() + " B");
        mockMvc.perform(MockMvcRequestBuilders.post("/locations").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(NEW_VALID_LOCATION))).andExpect(status().isCreated());
    }

    @Test
    void findAllLocations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/locations")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findLocationByIdMustReturnCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/locations/{id}", "L0001").accept("application/json"))
        .andExpect(status().isOk()).andExpect(jsonPath("$..id").value("L0001"))
        .andExpect(jsonPath("$.city").value("Sofia")).andExpect(jsonPath("$.name").value("PointBar"))
        .andExpect(jsonPath("$.locationCategory").value("BAR"));
    }

    @Test
    void findLocationByIdMustReturnExpectedStatusIfNotPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/locations/{id}", "E0001").accept("application/json"))
        .andExpect(status().isNotFound());
    }

    @Test
    void updateLocationWithInvalidLocationMustReturnValidationErrors() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/locations/{id}", "L0002").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(INVALID_LOCATION))).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name").value("*Name cannot be empty"))
        .andExpect(jsonPath("$.city").value("*City cannot be empty"))
        .andExpect(jsonPath("$.locationCategory").value("*Location Category cannot be not set"));
    }

    @Test
    void updateLocationWithValidLocationMustChangeTheAlreadyPresent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/locations/{id}", "L0001").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(UPDATED_VALID_LOCATION))).andExpect(status().isCreated());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private static <T> Set<ConstraintViolation<T>> getConstraintViolations(final T object) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        return validator.validate(object);
    }

    private class LocationDTOArgumentMatcher implements ArgumentMatcher<LocationDTO> {
        private final LocationDTO toMatch;

        public LocationDTOArgumentMatcher(final LocationDTO toMatch) {
            this.toMatch = toMatch;
        }

        @Override
        public boolean matches(final LocationDTO argument) {
            return argument.getId().equals(toMatch.getId());
        }

    }

}
