package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTransformerTest {

    @Test
    void testTransform() throws Exception {
        User user = UserFixture.TEST_USER;
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.apply(user);
        assertThat(dto, notNullValue());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
    }

    @Test
    void shouldTransformNullIntoDtoInstance() throws Exception {
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.apply(null);
        assertNotNull(dto);
    }

}