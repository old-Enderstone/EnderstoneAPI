package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.types.NumberAction;

public class IntProperty extends NumberProperty<Integer> {

    public IntProperty(final PropertyKey<Integer> key,
                       final Integer value,
                       final NumberAction<Integer> addAction,
                       final NumberAction<Integer> subtractAction,
                       final NumberAction<Integer> multiplyAction,
                       final NumberAction<Integer> divideAction) {
        super(key, value, addAction, subtractAction, multiplyAction, divideAction);
    }

    @Override
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Integer.parseInt(value));
    }

}
