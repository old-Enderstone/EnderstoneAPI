package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class DoubleProperty extends AbstractSystemProperty<Double> {

    public DoubleProperty(SystemProperty key, Double value) {
        super(key, value);
    }

    public abstract Double add(Double value);

    public abstract Double subtract(Double value);

    public abstract Double multiply(Double value);

    public abstract Double divide(Double value);

}
