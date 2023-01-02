package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class LongProperty extends NumberProperty<Long> {

    public LongProperty(final PropertyKey<Long> key, final Long value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        set(Long.parseLong(value));
    }

    @Override
    public Long add(final Long number) {
        return performAction(number, Long::sum);
    }

    @Override
    public Long subtract(final Long number) {
        return performAction(number, (n1, n2) -> n1 - n2);
    }

    @Override
    public Long multiply(final Long number) {
        return performAction(number, (n1, n2) -> n1 * n2);
    }

    @Override
    public Long divide(final Long number) {
        return performAction(number, (n1, n2) -> n1 / n2);
    }
}
