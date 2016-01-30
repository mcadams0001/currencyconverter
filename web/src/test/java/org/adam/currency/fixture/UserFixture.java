package org.adam.currency.fixture;

import org.adam.currency.builder.UserBuilder;
import org.adam.currency.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserFixture {
    public static final User TEST_USER = new UserBuilder().withName("test_user").withPassword("1234567890").withFirstName("Test").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1981, 4, 1)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();
    public static final User TEST_USER2 = new UserBuilder().withName("test_user2").withPassword("1234567890").withFirstName("Test2").withLastName("User").withEmailAddress("test_user@domain.com").withBirthDate(LocalDate.of(1985, 5, 2)).withAddress(AddressFixture.TEST_ADDRESS).withRoles(RoleFixture.ROLES).build();

    public static final List<User> USERS = new ArrayList<>(Arrays.asList(TEST_USER, TEST_USER2));
}
