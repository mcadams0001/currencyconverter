package org.adam.currency.builder;

import org.adam.currency.domain.User;
import org.adam.currency.fixture.CountryFixture;
import org.adam.currency.fixture.RoleFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class UserBuilderTest {
    @Test
    void createUser() {
        User user = new User();
        user.setId(100L);
        user.setName("test_user");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmailAddress("first_last@email.org");
        user.setCreateDate(LocalDateTime.of(2017,4,28,21,7,30));
        user.setBirthDate(LocalDate.of(1980,1,1));
        user.setAddress(new AddressBuilder().withId(200L).withStreet("5 The SouthSea").withCity("London").withPostCode("W3 4TW").withCountry(CountryFixture.UK).withCreateDate(LocalDateTime.of(2017,4,28,21,8,30)).build());
        user.setRoles(RoleFixture.ROLES);
        User actualUser = new UserBuilder()
                .withId(user.getId())
                .withName(user.getName())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmailAddress(user.getEmailAddress())
                .withCreateDate(user.getCreateDate())
                .withBirthDate(user.getBirthDate())
                .withAddress(user.getAddress())
                .withRoles(user.getRoles())
                .build();
        assertEquals(user, actualUser);
    }

    @Test
    void notIncludeNullRole() {
        User user = new UserBuilder().withRoles(null).build();
        assertNotNull(user.getRoles());
    }
}