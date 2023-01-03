package net.enderstone.api.common.properties;

import java.util.Locale;

public class Properties {

    public static final PropertyKeyRegistry registry = new PropertyKeyRegistry();

    public static final PropertyKey<Integer> PLAYER_COINS = registry.createIntKey("coins", 0, false);
    public static final PropertyKey<Integer> PLAYER_PLAYTIME = registry.createIntKey("playtime", 0, false);
    public static final PropertyKey<Long> PLAYER_LAST_SEEN = registry.createLongKey("last_seen", null, true);
    public static final PropertyKey<String> PLAYER_LAST_COUNTRY = registry.createStringKey("last_country", null, true);
    public static final PropertyKey<Boolean> PLAYER_ACCEPT_TOS = registry.createBooleanProperty("accept_tos", false, false);
    public static final PropertyKey<Locale> PLAYER_LOCALE = registry.createLocaleKey("locale", new Locale("DE", "de"), false);

    public static final PropertyKey<String> SYSTEM_MOTD = registry.createStringKey("motd", "Default MOTD", false);
    public static final PropertyKey<String> SYSTEM_MOTD_MAINTENANCE = registry.createStringKey("motd_maintenance", "Default Maintenance MOTD", false);

    public static final PropertyKey<Boolean> SYSTEM_MAINTENANCE = registry.createBooleanProperty("maintenance",  false, false);
}
