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
}
