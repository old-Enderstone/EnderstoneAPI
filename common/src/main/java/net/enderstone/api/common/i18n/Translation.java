package net.enderstone.api.common.i18n;

import java.util.Locale;

public class Translation {

    private final String key;
    private final Locale locale;
    private String translation;

    public Translation(String key, Locale locale, String translation) {
        this.key = key;
        this.locale = locale;
        this.translation = translation;
    }

    public Translation setTranslation(String translation) {
        this.translation = translation;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getTranslation() {
        return translation;
    }
}
