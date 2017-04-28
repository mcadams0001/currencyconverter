package org.adam.currency.common;

/**
 * Defines parameters passed from a controller to a view.
 */
public enum Parameters {
    COMMAND("command"),
    COUNTRIES("countries"),
    USER("user"),
    CURRENCIES("currencies"),
    VIEW_NAME("viewName"),
    HISTORY("historyList"),
    ERROR("error");

    private String name;

    Parameters(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public java.lang.String toString() {
        return name;
    }
}
