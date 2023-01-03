package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class FloatProperty extends NumberProperty<Float> {

    public FloatProperty(final PropertyKey<Float> key, final Float value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Float.parseFloat(value));
    }

    @Override
    public Float add(final Float number) {
        return performAction(number, Float::sum);
    }

    @Override
    public Float subtract(final Float number) {
        return performAction(number, (n1, n2) -> n1 - n2);
    }

    @Override
    public Float multiply(final Float number) {
        return performAction(number, (n1, n2) -> n1 * n2);
    }

    @Override
    public Float divide(final Float number) {
        return performAction(number, (n1, n2) -> n1 / n2);
    }
}
