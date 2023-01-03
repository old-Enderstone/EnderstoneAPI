package net.enderstone.api.common.properties.impl;

import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.PropertyKey;

public class DoubleProperty extends NumberProperty<Double> {

    public DoubleProperty(final PropertyKey<Double> key, final Double value) {
        super(key, value);
    }

    @Override
    public void fromString(final String value) {
        if(value == null) {
            set(null);
            return;
        }
        set(Double.parseDouble(value));
    }

    @Override
    public Double add(final Double number) {
        return performAction(number, Double::sum);
    }

    @Override
    public Double subtract(final Double number) {
        return performAction(number, (n1, n2) -> n1 - n2);
    }

    @Override
    public Double multiply(final Double number) {
        return performAction(number, (n1, n2) -> n1 * n2);
    }

    @Override
    public Double divide(final Double number) {
        return performAction(number, (n1, n2) -> n1 / n2);
    }
}
