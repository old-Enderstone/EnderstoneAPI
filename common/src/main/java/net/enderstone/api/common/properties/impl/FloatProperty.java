package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.types.NumberAction;

public class FloatProperty extends NumberProperty<Float> {

    public FloatProperty(final PropertyKey<Float> key,
                         final Float value,
                         final NumberAction<Float> addAction,
                         final NumberAction<Float> subtractAction,
                         final NumberAction<Float> multiplyAction,
                         final NumberAction<Float> divideAction) {
        super(key, value, addAction, subtractAction, multiplyAction, divideAction);
    }

    @Override
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Float.parseFloat(value));
    }

}
