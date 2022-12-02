package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class IntegerProperty implements IProperty<Integer> {

    private SystemProperty key;
    private Integer value;

    public IntegerProperty(SystemProperty key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public Integer get() {
        return value;
    }

    @Override
    public void set(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getDefaultValue() {
        return (Integer)key.defaultValue;
    }

    public abstract Integer add(Integer value);

    public abstract Integer subtract(Integer value);

    public abstract Integer multiply(Integer value);

    public abstract Integer divide(Integer value);
    
}
