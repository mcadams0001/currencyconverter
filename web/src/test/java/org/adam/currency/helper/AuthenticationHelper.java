package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.when;

public class AuthenticationHelper {
    public static void setupPrincipalForUserDetails(User user, Authentication mockAuthentication) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        when(mockAuthentication.getPrincipal()).thenReturn(userDetails);
    }
}
