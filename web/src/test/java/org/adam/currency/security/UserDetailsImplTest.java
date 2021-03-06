package org.adam.currency.security;

import org.adam.currency.builder.RoleBuilder;
import org.adam.currency.builder.UserBuilder;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;
import org.adam.currency.fixture.AddressFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private static final User USER = UserFixture.TEST_USER;
    private UserDetailsImpl userDetails = new UserDetailsImpl(USER);

    @Test
    void testGetAuthorities() {
        List<Role> roles = new ArrayList<>();
        Role standardRole = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
        roles.add(standardRole);
        roles.add(new Role(3L, null, null));
        User user = new UserBuilder().withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(roles).build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        List<String> expectedRoles = USER.getRoles().stream().map(r -> r.getName().name()).collect(toList());
        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(expectedRoles.size(), authorities.size());
        List<String> actualRoles = authorities.stream().map(GrantedAuthority::getAuthority).collect(toList());
        assertEquals(Collections.singletonList(RoleNameEnum.ROLE_USER.name()), actualRoles);
    }

    @Test
    void testGetPassword() {
        assertEquals(USER.getPassword(), userDetails.getPassword());
    }

    @Test
    void testGetUsername() {
        assertEquals(USER.getName(), userDetails.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testGetUser() {
        assertEquals(USER, userDetails.getUser());
    }
}
