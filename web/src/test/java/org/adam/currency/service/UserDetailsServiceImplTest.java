package org.adam.currency.service;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.domain.User;
import org.adam.currency.security.SecurityContextHelper;
import org.adam.currency.security.UserDetailsImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(value = MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    @Spy
    private UserDetailsServiceImpl service = new UserDetailsServiceImpl();
    @Mock
    private UserService mockUserService;
    @Mock
    private SecurityContext mockSecurityContext;
    @Mock
    private Authentication mockAuthentication;
    @Mock
    private SecurityContextHelper mockSecurityContextHelper;


    @Test
    public void testLoadUserByUsername() throws Exception {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(user).when(service).getUserFromSession();
        doNothing().when(service).setUserInSession(isA(User.class));
        UserDetails testUser = service.loadUserByUsername("test_user");
        verify(service).setUserInSession(isA(User.class));
        assertThat(testUser.getUsername(), equalTo("test_user"));
    }

    @Test
    public void shouldLoadUserByUserNameFromDb() throws Exception {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(null).when(service).getUserFromSession();
        doNothing().when(service).setUserInSession(isA(User.class));
        when(mockUserService.findUserByName(isA(String.class))).thenReturn(user);
        UserDetails testUser = service.loadUserByUsername("test_user");
        verify(mockUserService).findUserByName("test_user");
        verify(service).setUserInSession(isA(User.class));
        assertThat(testUser.getUsername(), equalTo("test_user"));
    }

    @Test
    public void testGetUserFromSession() throws Exception {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(new UserDetailsImpl(user));
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        verify(mockAuthentication).getPrincipal();
        assertThat(actualUser, equalTo(user));
    }

    @Test
    public void shouldGetUserFromSessionIfNotAuthenticated() throws Exception {
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(null);
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        assertThat(actualUser, nullValue());
    }

    @Test
    public void shouldGetUserFromSessionAndReturnNullOnWrongPrincipal() throws Exception {
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(1L);
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        verify(mockAuthentication).getPrincipal();
        assertThat(actualUser, nullValue());
    }

    @Test
    public void testSetUserInSession() throws Exception {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        service.setUserInSession(user);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(mockSecurityContext).setAuthentication(authenticationCaptor.capture());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = authenticationCaptor.getValue();
        assertThat(usernamePasswordAuthenticationToken, notNullValue());
        UserDetails userDetails = (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();
        assertThat(userDetails.getUsername(), equalTo(user.getName()));
    }

    @Test
    public void shouldGetSecurityContext() throws Exception {
        doReturn(mockSecurityContextHelper).when(service).getSecurityContextHelper();
        when(mockSecurityContextHelper.getSecurityContext()).thenReturn(mockSecurityContext);
        SecurityContext securityContext = service.getSecurityContext();
        verify(mockSecurityContextHelper).getSecurityContext();
        assertThat(securityContext, equalTo(mockSecurityContext));
    }

    @Test
    public void shouldGetSecurityContextHelper() throws Exception {
        SecurityContextHelper securityContextHelper = service.getSecurityContextHelper();
        assertThat(securityContextHelper, notNullValue());
    }
}