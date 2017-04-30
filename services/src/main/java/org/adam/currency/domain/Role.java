package org.adam.currency.domain;

import org.adam.currency.common.RoleNameEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLES")
public class Role implements Serializable {
    @Id
    @SequenceGenerator(name = "ROLE_ID_GEN", sequenceName = "ROLE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "ROLE_ID_GEN")
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLE_NAME")
    @Enumerated(EnumType.STRING)
    private RoleNameEnum name;

    @Column(name = "ROLE_DESC")
    private String description;

    public Role() {
    }

    public Role(Long id, RoleNameEnum name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public RoleNameEnum getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        return new EqualsBuilder()
                .append(id, role.id)
                .append(name, role.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "name=" + name +
                '}';
    }
}
