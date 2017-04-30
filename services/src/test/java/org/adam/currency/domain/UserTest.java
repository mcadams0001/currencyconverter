package org.adam.currency.domain;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.fixture.AddressFixture;
import org.adam.currency.fixture.RoleFixture;
import org.adam.currency.fixture.UserFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class UserTest {
    private User user1 = new UserBuilder().withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();
    private User user2 = new UserBuilder().withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();
    private User user3 = UserFixture.TEST_USER2;

    @Test
    public void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(user1, user2, user3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(user1, user2, user3);
    }

    @Test
    public void verifyToString() throws Exception {
        assertThat(user1.toString(), equalTo("User{id=null, name='test_user'}"));
    }
}