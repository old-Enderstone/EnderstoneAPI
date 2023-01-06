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
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Double.parseDouble(value));
    }

}
