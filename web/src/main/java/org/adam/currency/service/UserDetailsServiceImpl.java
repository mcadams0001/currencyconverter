package org.adam.currency.service;

import org.adam.currency.domain.User;
import org.adam.currency.security.SecurityContextHelper;
import org.adam.currency.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = getUserFromSession();
        if (user == null) {
            user = userService.findUserByName(userName);
            if(user == null) {
                throw new UsernameNotFoundException("User or password not found");
            }
        }
        setUserInSession(user);
        return new UserDetailsImpl(user);

    }

    User getUserFromSession() {
        SecurityContext context = getSecurityContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) principal;
                return userDetails.getUser();
            }
        }
        return null;
    }

    SecurityContext getSecurityContext() {
        return getSecurityContextHelper().getSecurityContext();
    }

    SecurityContextHelper getSecurityContextHelper() {
        return new SecurityContextHelper();
    }

    void setUserInSession(User user) {
        SecurityContext context = getSecurityContext();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        context.setAuthentication(authentication);
    }


}

