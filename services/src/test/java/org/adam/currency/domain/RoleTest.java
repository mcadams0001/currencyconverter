package org.adam.currency.domain;

import org.adam.currency.builder.RoleBuilder;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.fixture.RoleFixture;
import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class RoleTest {
    private Role role1 = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
    private Role role2 = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
    private Role role3 = new RoleBuilder().withId(2L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User2").build();

    @Test
    public void verifyEquals() throws Exception {
        EqualsTestHelper.verifyEquals(role1, role2 ,role3);
    }

    @Test
    public void verifyHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(role1, role2 ,role3);
    }

    @Test
    public void verifyToString() throws Exception {
        assertThat(role1.toString(), equalTo("Role{name=ROLE_USER}"));
    }
}