package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class FloatProperty implements IProperty<Float> {

    private final SystemProperty key;
    private Float value;

    public FloatProperty(SystemProperty key, Float value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public void set(Float value) {
        this.value = value;
    }
}
