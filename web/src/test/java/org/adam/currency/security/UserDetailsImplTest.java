package org.adam.currency.security;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.UserFixture;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UserDetailsImplTest {

    private static final User USER = UserFixture.TEST_USER;
    private UserDetailsImpl userDetails = new UserDetailsImpl(USER);

    @Test
    public void testGetAuthorities() throws Exception {
        List<String> expectedRoles = USER.getRoles().stream().map(r -> r.getName().name()).collect(toList());
        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
        assertThat(authorities, notNullValue());
        assertThat(authorities.size(), equalTo(expectedRoles.size()));
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(toList());
        assertThat(roles, equalTo(expectedRoles));
    }

    @Test
    public void testGetPassword() throws Exception {
        assertThat(userDetails.getPassword(), equalTo(USER.getPassword()));
    }

    @Test
    public void testGetUsername() throws Exception {
        assertThat(userDetails.getUsername(), equalTo(USER.getName()));
    }

    @Test
    public void testIsAccountNonExpired() throws Exception {
        assertThat(userDetails.isAccountNonExpired(), equalTo(true));
    }

    @Test
    public void testIsAccountNonLocked() throws Exception {
        assertThat(userDetails.isAccountNonLocked(), equalTo(true));
    }

    @Test
    public void testIsCredentialsNonExpired() throws Exception {
        assertThat(userDetails.isCredentialsNonExpired(), equalTo(true));
    }

    @Test
    public void testIsEnabled() throws Exception {
        assertThat(userDetails.isEnabled(), equalTo(true));
    }

    @Test
    public void testGetUser() throws Exception {
        assertThat(userDetails.getUser(), equalTo(USER));
    }
}
