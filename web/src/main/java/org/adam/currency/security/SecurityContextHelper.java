package org.adam.currency.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {
    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
}
