package org.adam.currency.domain;

import org.adam.currency.common.SettingField;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "SETTINGS")
public class Setting {
    @Id
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private SettingField name;

    @Column(name = "VAL")
    private String value;

    public Setting() {
    }

    public Setting(SettingField name, String value) {
        this.name = name;
        this.value = value;
    }

    public SettingField getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Setting setting = (Setting) o;

        return new EqualsBuilder()
                .append(name, setting.name)
                .append(value, setting.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Setting{" + name + "=" + value + '}';
    }
}
