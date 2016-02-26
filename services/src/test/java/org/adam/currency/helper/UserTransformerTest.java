package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.dto.UserDTO;
import org.adam.currency.fixture.UserFixture;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class UserTransformerTest {

    @Test
    public void testTransform() throws Exception {
        User user = UserFixture.TEST_USER;
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.transform(user);
        assertThat(dto, notNullValue());
        assertThat(dto.getFirstName(), equalTo(user.getFirstName()));
        assertThat(dto.getLastName(), equalTo(user.getLastName()));
    }

    @Test
    public void shouldTransformNullIntoDtoInstance() throws Exception {
        UserTransformer transformer = new UserTransformer();
        UserDTO dto = transformer.transform(null);
        assertThat(dto, notNullValue());
    }

}