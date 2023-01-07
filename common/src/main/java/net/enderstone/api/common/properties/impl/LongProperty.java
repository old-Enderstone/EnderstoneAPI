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
    public Long add(final Long t1, final Long t2) {
        return t1 + t2;
    }

    @Override
    public Long sub(final Long t1, final Long t2) {
        return t1 - t2;
    }

    @Override
    public Long mul(final Long t1, final Long t2) {
        return t1 * t2;
    }

    @Override
    public Long div(final Long t1, final Long t2) {
        return t1 / t2;
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        if(value == null) {
            super.value = null;
            return;
        }
        super.value = Long.parseLong(value);
    }

}
