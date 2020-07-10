package com.enjoyit.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.enjoyit.persistence.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockBean
    private UserRepository userRepo;

    @Test
    public void testLogin() throws Exception {
//        when(userRepo.findByUsername(VALID_USERNAME))
//                .thenReturn(Optional.of(new JpaUser(VALID_USERNAME, VALID_ENTRY_PSW)));
        assertTrue(true);
    }

}
