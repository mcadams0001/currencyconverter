package org.adam.currency.fixture;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.domain.User;

public class UserFixture {
    public static final User TEST_USER = new UserBuilder().withId(1L).withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();
}
