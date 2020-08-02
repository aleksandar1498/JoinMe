package com.enjoyit.web.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private static final UserLoginDTO INVALID_LOGIN = new UserLoginDTO("alex123", "invalid");
    private static final UserLoginDTO VALID_LOGIN = new UserLoginDTO("fani123", "123");
    private static final UserRegisterDTO VALID_REGISTER = new UserRegisterDTO("marco", "marco", "montanaro", "marco@gmail.com", "123", "123", Boolean.TRUE);
    private static final UserWithRolesDTO  REGISTERED = new UserWithRolesDTO("U0001","marco", "marco", "montanaro", "marco@gmail.com", Boolean.TRUE);

    private static final UserRegisterDTO INVALID_REGISTER = new UserRegisterDTO("marco", "marco", "montanaro", "marco@gmail.com", "123", "321", Boolean.TRUE);

    @Test
    void loginWithWrongCredentialsMustThrowExpected() throws Exception {
        when(authService.login(ArgumentMatchers.argThat(new UserLoginDTOArgumentMatcher(INVALID_LOGIN)))).thenThrow(BadCredentialsException.class);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(INVALID_LOGIN)))
        .andExpect(status().isUnauthorized());

    }

    @Test
    void loginWithCorrectCredentialsMustReturnExpected() throws Exception {
        when(authService.login(ArgumentMatchers.argThat(new UserLoginDTOArgumentMatcher(VALID_LOGIN)))).thenReturn(new LoggedInUserDTO("alex123", "token"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(VALID_LOGIN)))
        .andExpect(status().isOk());

    }

    @Test
    void registerValidUser() throws Exception {
        when(authService.register(ArgumentMatchers.argThat(new UserRegisterDTOArgumentMatcher(VALID_REGISTER)))).thenReturn(REGISTERED);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(VALID_REGISTER)))
        .andExpect(status().isCreated());

    }

    @Test
    void registerNotValidUser() throws Exception {
        when(authService.register(ArgumentMatchers.argThat(new UserRegisterDTOArgumentMatcher(INVALID_REGISTER)))).thenThrow(ConstraintViolationException.class);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(INVALID_REGISTER)))
        .andExpect(status().isBadRequest());

    }

    private class UserLoginDTOArgumentMatcher implements ArgumentMatcher<UserLoginDTO> {
        private final UserLoginDTO toMatch;

        public UserLoginDTOArgumentMatcher(final UserLoginDTO toMatch) {
            this.toMatch = toMatch;
        }

        @Override
        public boolean matches(final UserLoginDTO argument) {
            return argument.getUsername().equals(toMatch.getUsername());
        }

    }

    private class UserRegisterDTOArgumentMatcher implements ArgumentMatcher<UserRegisterDTO> {
        private final UserRegisterDTO toMatch;

        public UserRegisterDTOArgumentMatcher(final UserRegisterDTO toMatch) {
            this.toMatch = toMatch;
        }

        @Override
        public boolean matches(final UserRegisterDTO argument) {
            return argument.getUsername().equals(toMatch.getUsername());
        }

    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
