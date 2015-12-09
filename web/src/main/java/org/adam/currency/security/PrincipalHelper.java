package org.adam.currency.security;

import org.adam.currency.domain.User;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class PrincipalHelper {

    PrincipalHelper() {

    }

    public static User getUserFromPrincipal(Principal principal) {
        Authentication authentication = (Authentication) principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
