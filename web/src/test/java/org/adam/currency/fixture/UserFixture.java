package org.adam.currency.fixture;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.domain.User;

import java.time.LocalDate;

public class UserFixture {
    public static final User TEST_USER = new UserBuilder().withId(1L).withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();
}
