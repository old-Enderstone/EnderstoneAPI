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
    public Integer add(final Integer t1, final Integer t2) {
        return t1 + t2;
    }

    @Override
    public Integer sub(final Integer t1, final Integer t2) {
        return t1 - t2;
    }

    @Override
    public Integer mul(final Integer t1, final Integer t2) {
        return t1 * t2;
    }

    @Override
    public Integer div(final Integer t1, final Integer t2) {
        return t1 / t2;
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        if(value == null) {
            super.value = null;
            return;
        }
        super.value = Integer.parseInt(value);
    }

}
