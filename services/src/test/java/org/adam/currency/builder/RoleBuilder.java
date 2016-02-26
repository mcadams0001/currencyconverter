package org.adam.currency.builder;

import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;

public class RoleBuilder {
    private Long id;
    private RoleNameEnum name;
    private String description;

    public RoleBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public RoleBuilder withName(RoleNameEnum name){
        this.name = name;
        return this;
    }

    public RoleBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public Role build() {
        return new Role(id, name, description);
    }
}
