package net.enderstone.api.common.properties;

public enum SystemProperty {

    MAINTENANCE(true),
    MOTD("§9MOTD Default value"),
    SLOTS(50);

    public final Object defaultValue;

    SystemProperty(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
