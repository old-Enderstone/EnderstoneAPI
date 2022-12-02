package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class LongProperty implements IProperty<Long> {

    private final SystemProperty key;
    private Long value;

    public LongProperty(SystemProperty key, Long value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public Long get() {
        return value;
    }

    @Override
    public void set(Long value) {
        this.value = value;
    }

    @Override
    public Long getDefaultValue() {
        return (Long)key.defaultValue;
    }

    public abstract Long add(Long value);

    public abstract Long subtract(Long value);

    public abstract Long multiply(Long value);

    public abstract Long divide(Long value);
    
}
