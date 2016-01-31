package org.adam.currency.service;

import org.adam.currency.command.UserCommand;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Address;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.RoleFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.isA;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private GenericService mockGenericService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private CountryService mockCountryService;

    @Test
    public void testFindUserByName() throws Exception {
        User expectedUser = UserFixture.TEST_USER;
        when(mockGenericService.findByName(User.class, "name", "test_user")).thenReturn(expectedUser);
        User user = userService.findUserByName("test_user");
        verify(mockGenericService).findByName(User.class, "name", "test_user");
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserCommand command = createCommand();
        String encodedPassword = "A1B2C3D4";
        when(mockPasswordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(mockCountryService.findByCode("UK")).thenReturn(CountryFixture.UK);
        when(mockGenericService.findByName(isA(Class.class), anyString(), anyString())).thenReturn(RoleFixture.ROLE_USER);
        User user = userService.createUser(command);
        verify(mockPasswordEncoder).encode("password1");
        verify(mockCountryService).findByCode("UK");
        verify(mockGenericService, times(2)).save(anyObject());
        assertThat(user, notNullValue());
        assertThat(user.getAddress(), notNullValue());
        assertThat(user.getBirthDate(), equalTo(LocalDate.of(2016, 1, 1)));
        assertThat(user.getFirstName(), equalTo(command.getFirstName()));
        assertThat(user.getLastName(), equalTo(command.getLastName()));
        assertThat(user.getName(), equalTo(command.getName()));
        assertThat(user.getPassword(), equalTo(encodedPassword));
        assertThat(user.getRoles(), hasItem(RoleFixture.ROLE_USER));
        assertThat(user.getAddress().getStreet(), equalTo(command.getStreet()));
        assertThat(user.getAddress().getCity(), equalTo(command.getCity()));
        assertThat(user.getAddress().getCountry(), equalTo(CountryFixture.UK));
        assertThat(user.getAddress().getPostCode(), equalTo(command.getPostCode()));
    }

    @Test
    public void shouldFindRoleByName() throws Exception {
        RoleNameEnum roleName = RoleNameEnum.ROLE_USER;
        when(mockGenericService.findByName(eq(Role.class), anyString(), anyString())).thenReturn(RoleFixture.ROLE_USER);
        Role role = userService.findRoleByName(roleName);
        verify(mockGenericService).findByName(Role.class, "name", roleName);
        assertThat(role, notNullValue());
        assertThat(role.getName(), equalTo(roleName));
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