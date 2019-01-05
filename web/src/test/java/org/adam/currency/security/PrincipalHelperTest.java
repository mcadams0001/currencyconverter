package org.adam.currency.security;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrincipalHelperTest {

    @Mock
    private Authentication mockAuthentication;

    @Test
    void getUserFromPrincipal() {
        new PrincipalHelper();
        User expectedUser = UserFixture.TEST_USER;
        UserDetailsImpl userDetails = new UserDetailsImpl(expectedUser);
        when(mockAuthentication.getPrincipal()).thenReturn(userDetails);
        User user = PrincipalHelper.getUserFromPrincipal(mockAuthentication);
        verify(mockAuthentication).getPrincipal();
        assertEquals(expectedUser, user);
    }

}