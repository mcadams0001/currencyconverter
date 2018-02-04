package org.adam.currency.domain;

import org.adam.currency.builder.RoleBuilder;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {
    private Role role1 = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
    private Role role2 = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
    private Role role3 = new RoleBuilder().withId(2L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User2").build();

    @Test
    void verifyEquals() {
        EqualsTestHelper.verifyEquals(role1, role2, role3);
    }

    @Test
    void verifyHashCode() {
        EqualsTestHelper.verifyHashCode(role1, role2, role3);
    }

    @Test
    void verifyToString() {
        assertEquals("Role{name=ROLE_USER}", role1.toString());
    }
}