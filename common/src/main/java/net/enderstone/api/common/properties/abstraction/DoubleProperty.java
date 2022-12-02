package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class DoubleProperty implements IProperty<Double> {

    private final SystemProperty key;
    private Double value;

    public DoubleProperty(SystemProperty key, Double value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public Double get() {
        return value;
    }

    @Override
    public void set(Double value) {
        this.value = value;
    }
}
