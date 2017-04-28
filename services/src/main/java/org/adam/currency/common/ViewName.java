package org.adam.currency.common;

/**
 * Defines names of the views.
 */
public enum ViewName {
    LOGIN("login"),
    INDEX("index");

    private String name;

    ViewName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
