package org.adam.currency.builder;

import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleBuilderTest {
    @Test
    void createRole() {
        Role role = new Role(1L, RoleNameEnum.ROLE_USER, "Standard user");
        Role actual = new RoleBuilder().withId(role.getId()).withName(role.getName()).withDescription(role.getDescription()).build();
        assertEquals(role, actual);
    }
}