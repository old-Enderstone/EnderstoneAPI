package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;

import java.util.Locale;

public class LocaleProperty extends AbstractProperty<Locale> {

    public LocaleProperty(final PropertyKey<Locale> key, final Locale value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        if(value == null) {
            super.value = null;
            return;
        }
        super.value = Locale.forLanguageTag(value);
    }

    @Override
    public String asString() {
        return super.value.toLanguageTag();
    }

}

