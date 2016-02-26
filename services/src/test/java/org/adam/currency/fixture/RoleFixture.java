package org.adam.currency.fixture;

import org.adam.currency.builder.RoleBuilder;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;

import java.util.Collections;
import java.util.List;

public class RoleFixture {
    public static final Role ROLE_USER = new RoleBuilder().withId(1L).withName(RoleNameEnum.ROLE_USER).withDescription("Standard User").build();
    public static final List<Role> ROLES = Collections.unmodifiableList(Collections.singletonList(ROLE_USER));
}
