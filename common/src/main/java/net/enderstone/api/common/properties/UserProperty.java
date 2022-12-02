package net.enderstone.api.common.properties;

import java.util.Locale;

public enum UserProperty {

    LAST_SEEN(null),
    LAST_IP(null),
    LAST_COUNTRY(null),
    LOCALE(new Locale("en", "us")),
    PLAYTIME(0),
    COINS(100);

    public final Object defaultValue;

    UserProperty(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
