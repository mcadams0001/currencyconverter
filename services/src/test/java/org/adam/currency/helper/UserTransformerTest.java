package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTransformerTest {

    @Test
    void testTransform() {
        User user = UserFixture.TEST_USER;
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.apply(user);
        assertNotNull(dto);
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
    }

    @Test
    void shouldTransformNullIntoDtoInstance() {
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.apply(null);
        assertNotNull(dto);
    }

}