package org.adam.currency.builder;

import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RoleBuilderTest {
    @Test
    public void createRole() throws Exception {
        Role role = new Role(1L, RoleNameEnum.ROLE_USER, "Standard user");
        Role actual = new RoleBuilder().withId(role.getId()).withName(role.getName()).withDescription(role.getDescription()).build();
        assertThat(actual, equalTo(role));
    }
}