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
    public Float add(final Float t1, final Float t2) {
        return t1 + t2;
    }

    @Override
    public Float sub(final Float t1, final Float t2) {
        return t1 - t2;
    }

    @Override
    public Float mul(final Float t1, final Float t2) {
        return t1 * t2;
    }

    @Override
    public Float div(final Float t1, final Float t2) {
        return t1 / t2;
    }

    @Override
    public void fromString(final String value) {
        super.checkNullValue(value == null);
        if(value == null) {
            super.value = null;
            return;
        }
        super.value = Float.parseFloat(value);
    }

}
