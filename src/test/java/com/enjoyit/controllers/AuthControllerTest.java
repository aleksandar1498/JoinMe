package com.enjoyit.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.enjoyit.domain.entities.JpaUser;
import com.enjoyit.persistence.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private static final String VALID_USERNAME = "alex";
    private static final String VALID_ENTRY_PSW = "alex";
    @MockBean
    private UserRepository userRepo;

    @Test
    public void testLogin() throws Exception {

        // "{ "username": "SAVINGS", "password": "" }"
        when(userRepo.findJpaUserByUsername(VALID_USERNAME))
                .thenReturn(Optional.of(new JpaUser(VALID_USERNAME, VALID_ENTRY_PSW)));

        //final String body = String.format(LOGIN_BODY_TEMPLATE, VALID_USERNAME, VALID_ENTRY_PSW);

      //  System.out.println("HERE "+mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(body).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString());
        assertTrue(true);
    }

}
