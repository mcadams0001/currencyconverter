package org.adam.currency.service;

import org.adam.currency.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private GenericService mockGenericService;

    @Test
    public void testFindUserByName() throws Exception {
        User expectedUser = new User();
        when(mockGenericService.findByName(User.class, "name", "test_user")).thenReturn(expectedUser);
        User user = userService.findUserByName("test_user");
        verify(mockGenericService).findByName(User.class, "name", "test_user");
        assertThat(user, sameInstance(expectedUser));
    }

    @Test
    public void shouldCreateUser() throws Exception {

    }

}