package net.enderstone.api.common.properties;

import java.util.Locale;

public enum UserProperty {

    LAST_SEEN(PropertyType.LONG, 0L),
    LAST_COUNTRY(PropertyType.STRING, null),
    LOCALE(PropertyType.STRING, new Locale("en", "us").toString()),
    PLAYTIME(PropertyType.INTEGER, 0),
    COINS(PropertyType.INTEGER, 100),
    ACCEPT_TOS(PropertyType.BOOLEAN, false);

    public final PropertyType type;
    public final Object defaultValue;

    UserProperty(final PropertyType type, final Object defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }
}
