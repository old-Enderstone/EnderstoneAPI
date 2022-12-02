package net.enderstone.api.common.properties;

public enum SystemProperty {

    MAINTENANCE(PropertyType.BOOLEAN, true),
    MOTD(PropertyType.STRING, "§9MOTD Default value"),
    SLOTS(PropertyType.INTEGER, 50);

    public final PropertyType type;
    public final Object defaultValue;

    SystemProperty(final PropertyType type, final Object defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }
}
