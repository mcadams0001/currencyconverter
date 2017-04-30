package org.adam.currency.jmx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatusMBeanImplTest {

    @InjectMocks
    private StatusMBeanImpl bean = new StatusMBeanImpl();

    @Mock
    private SessionRegistry mockSessionRegistry;

    @Test
    public void testGetCurrentActiveUsers() throws Exception {
        List<Object> list = new ArrayList<>();
        list.add(new User("user1", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        list.add(new User("user2", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        list.add("Invalid object");
        when(mockSessionRegistry.getAllPrincipals()).thenReturn(list);
        List<String> userNames = bean.getCurrentActiveUsers();
        verify(mockSessionRegistry).getAllPrincipals();
        assertThat(userNames, notNullValue());
        assertThat(userNames, hasItems("user1", "user2"));
    }
}