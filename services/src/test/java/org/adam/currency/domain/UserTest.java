package org.adam.currency.domain;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.fixture.AddressFixture;
import org.adam.currency.fixture.RoleFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private UserBuilder builder = new UserBuilder().withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES);
    private User user1 = builder.build();
    private User user2 = builder.build();
    private User user3 = UserFixture.TEST_USER2;

    @Test
    void verifyEquals() {
        EqualsTestHelper.verifyEquals(user1, user2, user3);
    }

    @Test
    void verifyHashCode() {
        EqualsTestHelper.verifyHashCode(user1, user2, user3);
    }

    @Test
    void verifyToString() {
        assertEquals("User{id=null, name='test_user'}", user1.toString());
    }
}