package org.adam.currency.service;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.domain.User;
import org.adam.currency.security.SecurityContextHelper;
import org.adam.currency.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
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
    void testLoadUserByUsername() {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(user).when(service).getUserFromSession();
        doNothing().when(service).setUserInSession(isA(User.class));
        UserDetails testUser = service.loadUserByUsername("test_user");
        verify(service).setUserInSession(isA(User.class));
        assertEquals("test_user", testUser.getUsername());
    }

    @Test
    void shouldLoadUserByUserNameFromDb() {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(null).when(service).getUserFromSession();
        doNothing().when(service).setUserInSession(isA(User.class));
        when(mockUserService.findUserByName(isA(String.class))).thenReturn(user);
        UserDetails testUser = service.loadUserByUsername("test_user");
        verify(mockUserService).findUserByName("test_user");
        verify(service).setUserInSession(isA(User.class));
        assertEquals("test_user", testUser.getUsername());
    }

    @Test
    void shouldThrowExceptionOnNonExistingUser() {
        doReturn(null).when(service).getUserFromSession();
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("test_user");
        });
    }

    @Test
    void testGetUserFromSession() {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(new UserDetailsImpl(user));
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        verify(mockAuthentication).getPrincipal();
        assertEquals(user, actualUser);
    }

    @Test
    void shouldGetUserFromSessionIfNotAuthenticated() {
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(null);
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        assertNull(actualUser);
    }

    @Test
    void shouldGetUserFromSessionAndReturnNullOnWrongPrincipal() {
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(1L);
        User actualUser = service.getUserFromSession();
        verify(mockSecurityContext).getAuthentication();
        verify(mockAuthentication).getPrincipal();
        assertNull(actualUser);
    }

    @Test
    void testSetUserInSession() {
        User user = new UserBuilder().withId(1L).withName("test_user").build();
        doReturn(mockSecurityContext).when(service).getSecurityContext();
        service.setUserInSession(user);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(mockSecurityContext).setAuthentication(authenticationCaptor.capture());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = authenticationCaptor.getValue();
        assertNotNull(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();
        assertEquals(user.getName(), userDetails.getUsername());
    }

    @Test
    void shouldGetSecurityContext() {
        doReturn(mockSecurityContextHelper).when(service).getSecurityContextHelper();
        when(mockSecurityContextHelper.getSecurityContext()).thenReturn(mockSecurityContext);
        SecurityContext securityContext = service.getSecurityContext();
        verify(mockSecurityContextHelper).getSecurityContext();
        assertEquals(mockSecurityContext, securityContext);
    }

    @Test
    void shouldGetSecurityContextHelper() {
        SecurityContextHelper securityContextHelper = service.getSecurityContextHelper();
        assertNotNull(securityContextHelper);
    }
}