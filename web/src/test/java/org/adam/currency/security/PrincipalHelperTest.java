package org.adam.currency.security;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.UserFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrincipalHelperTest {

    @Mock
    private Authentication mockAuthentication;

    @Test
    public void getUserFromPrincipal() throws Exception {
        new PrincipalHelper();
        User expectedUser = UserFixture.TEST_USER;
        UserDetailsImpl userDetails = new UserDetailsImpl(expectedUser);
        when(mockAuthentication.getPrincipal()).thenReturn(userDetails);
        User user = PrincipalHelper.getUserFromPrincipal(mockAuthentication);
        verify(mockAuthentication).getPrincipal();
        assertThat(user, equalTo(expectedUser));
    }

}