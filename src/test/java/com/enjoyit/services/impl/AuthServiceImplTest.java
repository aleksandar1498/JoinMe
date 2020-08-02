package com.enjoyit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.enjoyit.domain.dto.LoggedInUserDTO;
import com.enjoyit.domain.dto.UserLoginDTO;
import com.enjoyit.domain.dto.UserRegisterDTO;
import com.enjoyit.domain.dto.UserWithRolesDTO;
import com.enjoyit.enums.UserRoles;
import com.enjoyit.persistence.entities.JpaUser;
import com.enjoyit.persistence.repositories.UserRepository;
import com.enjoyit.services.AuthService;

@SpringBootTest
class AuthServiceImplTest {

    private static final String VALID_USERNAME = "ALEKSANDAR98";
    private static final String INVALID_USERNAME = "abcd";
    private static final String VALID_ENTRY_PSW = "123";
    private static final String INVALID_ENTRY_PSW = "0000";

    static Stream<Arguments> registerArguments() {
        return Stream.of(
                Arguments.arguments(
                        new UserRegisterDTO("alex111", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com", "123", "123"),
                        UserRoles.ADMIN),
                Arguments.arguments(new UserRegisterDTO("alex12", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com",
                        "123", "123", Boolean.TRUE), UserRoles.ORGANIZER),
                Arguments.arguments(new UserRegisterDTO("alex123", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com",
                        "123", "123", Boolean.FALSE), UserRoles.USER));

    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCrypt;
    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        this.clearData();
    }
    @AfterEach
    public void tearDown() {
        this.clearData();
    }
    private void clearData() {
        this.userRepository.deleteAll();
    }

    @Test
    public void loginWithInvalidPasswordShouldThrowExpected() {

        this.authService.register(new UserRegisterDTO(VALID_USERNAME, "Aleksandar", "Stefanov",
                "aleksandar.fs@gmail.com", VALID_ENTRY_PSW, VALID_ENTRY_PSW, Boolean.FALSE));
        assertThrows(BadCredentialsException.class, () -> {
            this.authService.login(new UserLoginDTO(VALID_USERNAME, INVALID_ENTRY_PSW));
        });

    }

    @Test
    public void loginWithInvalidUsernameShouldThrowExpected() {

        assertThrows(InternalAuthenticationServiceException.class, () -> {
            this.authService.login(new UserLoginDTO(INVALID_USERNAME, VALID_ENTRY_PSW));
        });

    }

    @Test
    public void loginWithValidCredentialsButUserIsBannedShouldThrowExpected() {

        final JpaUser bannedUser = new JpaUser();
        bannedUser.setUsername(VALID_USERNAME);
        bannedUser.setPassword(bCrypt.encode(VALID_ENTRY_PSW));
        bannedUser.setBanned(Boolean.TRUE);
        this.userRepository.save(bannedUser);
        final Exception ex = assertThrows(InternalAuthenticationServiceException.class, () -> {
            this.authService.login(new UserLoginDTO(VALID_USERNAME, VALID_ENTRY_PSW));
        });
        assertEquals("You are not authorized to access,because you are banned", ex.getMessage(), "Assertion failed");

    }

    @Test
    public void loginWithValidCredentialsShouldBeSuccessful() {

        final UserWithRolesDTO userRegistered = this.authService.register(new UserRegisterDTO(VALID_USERNAME,
                "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com", VALID_ENTRY_PSW, VALID_ENTRY_PSW, Boolean.FALSE));
        final LoggedInUserDTO logged = this.authService.login(new UserLoginDTO(VALID_USERNAME, VALID_ENTRY_PSW));
        assertTrue(logged.getUsername().equals(userRegistered.getUsername()));

    }

    @Test
    public void registerAnUserWithNotMatchingPasswordMustThrowExpected() {

        final UserRegisterDTO toRegister = new UserRegisterDTO(VALID_USERNAME, "Aleksandar", "Stefanov",
                "aleksandar.fs@gmail.com", "123", "1323", Boolean.FALSE);
        this.authService.register(toRegister);

        assertThrows(IllegalArgumentException.class, () -> this.authService.register(toRegister));

    }

    @Test
    public void registerAnUserAlreadyPresentMustThrowExpected() {

        final UserRegisterDTO toRegister = new UserRegisterDTO(VALID_USERNAME, "Aleksandar", "Stefanov",
                "aleksandar.fs@gmail.com", "123", "123", Boolean.FALSE);
        this.authService.register(toRegister);

        assertThrows(IllegalArgumentException.class, () -> this.authService.register(toRegister));

    }

    @Test
    public void registerWithValidData() {

        this.authService.register(new UserRegisterDTO("alex111", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com", "123", "123"));

        final UserWithRolesDTO user2 = this.authService.register(new UserRegisterDTO("alex1", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com", "123", "123",Boolean.TRUE));
        assertTrue(user2.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRoles.ORGANIZER)));

        final UserWithRolesDTO user3 = this.authService.register(new UserRegisterDTO("alex12", "Aleksandar", "Stefanov", "aleksandar.fs@gmail.com", "123", "123",Boolean.FALSE));
        assertTrue(user3.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRoles.USER)));

    }

}
