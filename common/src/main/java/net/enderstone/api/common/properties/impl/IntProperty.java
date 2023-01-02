package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class IntProperty extends NumberProperty<Integer> {

    public IntProperty(final PropertyKey<Integer> key, final Integer value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        set(Integer.parseInt(value));
    }

    @Override
    public Integer add(final Integer number) {
        return performAction(number, Integer::sum);
    }

    @Override
    public Integer subtract(final Integer number) {
        return performAction(number, (n1, n2) -> n1 - n2);
    }

    @Override
    public Integer multiply(final Integer number) {
        return performAction(number, (n1, n2) -> n1 * n2);
    }

    @Override
    public Integer divide(final Integer number) {
        return performAction(number, (n1, n2) -> n1 / n2);
    }
}
