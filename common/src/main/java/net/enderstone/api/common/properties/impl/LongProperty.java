package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.types.NumberAction;

public class LongProperty extends NumberProperty<Long> {

    public LongProperty(final PropertyKey<Long> key, final Long value,
                        final NumberAction<Long> addAction,
                        final NumberAction<Long> subtractAction,
                        final NumberAction<Long> multiplyAction,
                        final NumberAction<Long> divideAction) {
        super(key, value, addAction, subtractAction, multiplyAction, divideAction);
    }

    @Override
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Long.parseLong(value));
    }

}
