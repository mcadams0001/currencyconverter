package org.adam.currency.jmx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StatusMBeanImplTest {

    @InjectMocks
    private StatusMBeanImpl bean = new StatusMBeanImpl();

    @Mock
    private SessionRegistry mockSessionRegistry;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testGetCurrentActiveUsers() {
        List<Object> list = new ArrayList<>();
        list.add(new User("user1", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        list.add(new User("user2", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
        list.add("Invalid object");
        when(mockSessionRegistry.getAllPrincipals()).thenReturn(list);
        List<String> userNames = bean.getCurrentActiveUsers();
        verify(mockSessionRegistry).getAllPrincipals();
        assertNotNull(userNames);
        assertTrue(userNames.contains("user1"));
        assertTrue(userNames.contains("user2"));
    }
}