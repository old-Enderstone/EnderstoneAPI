package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class StringProperty extends AbstractProperty<String> {

    public StringProperty(final PropertyKey<String> key, final String value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        super.value = value;
    }
}
