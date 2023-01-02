package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class BooleanProperty extends AbstractProperty<Boolean> {

    public BooleanProperty(final PropertyKey<Boolean> key, final Boolean value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        set(Boolean.parseBoolean(value));
    }

    public boolean toggle() {
        super.lock.lock();

        final boolean newValue = !get();
        set(newValue);

        super.lock.unlock();
        return newValue;
    }

}
