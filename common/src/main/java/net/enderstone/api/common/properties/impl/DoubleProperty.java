package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.types.NumberAction;

public class DoubleProperty extends NumberProperty<Double> {

    public DoubleProperty(final PropertyKey<Double> key,
                          final Double value,
                          final NumberAction<Double> addAction,
                          final NumberAction<Double> subtractAction,
                          final NumberAction<Double> multiplyAction,
                          final NumberAction<Double> divideAction) {
        super(key, value, addAction, subtractAction, multiplyAction, divideAction);
    }

    @Override
    public Double add(final Double t1, final Double t2) {
        return t1 + t2;
    }

    @Override
    public Double sub(final Double t1, final Double t2) {
        return t1 - t2;
    }

    @Override
    public Double mul(final Double t1, final Double t2) {
        return t1 * t2;
    }

    @Override
    public Double div(final Double t1, final Double t2) {
        return t1 / t2;
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        if(value == null) {
            super.value = null;
            return;
        }
        super.value = Double.parseDouble(value);
    }

}
