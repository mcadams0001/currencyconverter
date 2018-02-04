package org.adam.currency.service;

import org.adam.currency.command.UserCommand;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.RoleFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private GenericService mockGenericService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private CountryService mockCountryService;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testFindUserByName() {
        User expectedUser = UserFixture.TEST_USER;
        when(mockGenericService.findByName(User.class, "name", "test_user")).thenReturn(expectedUser);
        User user = userService.findUserByName("test_user");
        verify(mockGenericService).findByName(User.class, "name", "test_user");
        assertSame(expectedUser, user);
    }

    @Test
    void shouldCreateUser() {
        UserCommand command = createCommand();
        String encodedPassword = "A1B2C3D4";
        when(mockPasswordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(mockCountryService.findByCode("UK")).thenReturn(CountryFixture.UK);
        when(mockGenericService.findByName(eq(Role.class), anyString(), eq(RoleNameEnum.ROLE_USER))).thenReturn(RoleFixture.ROLE_USER);
        User user = userService.createUser(command);
        verify(mockPasswordEncoder).encode("password1");
        verify(mockCountryService).findByCode("UK");
        verify(mockGenericService, times(2)).save(any());
        assertNotNull(user);
        assertNotNull(user.getAddress());
        assertEquals(LocalDate.of(2016, 1, 1), user.getBirthDate());
        assertEquals(command.getFirstName(), user.getFirstName());
        assertEquals(command.getLastName(), user.getLastName());
        assertEquals(command.getName(), user.getName());
        assertEquals(encodedPassword, user.getPassword());
        assertTrue(user.getRoles().contains(RoleFixture.ROLE_USER));
        assertEquals(command.getStreet(), user.getAddress().getStreet());
        assertEquals(command.getCity(), user.getAddress().getCity());
        assertEquals(CountryFixture.UK, user.getAddress().getCountry());
        assertEquals(command.getPostCode(), user.getAddress().getPostCode());
    }

    @Test
    void shouldFindRoleByName() {
        RoleNameEnum roleName = RoleNameEnum.ROLE_USER;
        when(mockGenericService.findByName(eq(Role.class), anyString(), eq(RoleNameEnum.ROLE_USER))).thenReturn(RoleFixture.ROLE_USER);
        Role role = userService.findRoleByName(roleName);
        verify(mockGenericService).findByName(Role.class, "name", roleName);
        assertNotNull(role);
        assertEquals(roleName, role.getName());
    }


    private UserCommand createCommand() {
        UserCommand command = new UserCommand();
        command.setName("user_name");
        command.setFirstName("firstName");
        command.setLastName("lastName");
        command.setBirthDate("01-Jan-2016");
        command.setEmail("email@email.com");
        command.setCountry("UK");
        command.setStreet("Road");
        command.setPostCode("TN13 1DD");
        command.setPassword("password1");
        command.setRepeatPassword("password1");
        return command;
    }

}