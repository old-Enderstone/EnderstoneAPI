package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class AbstractSystemProperty<T> implements IProperty<T> {

    private final SystemProperty key;
    private T value;

    public AbstractSystemProperty(SystemProperty key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public T get() {
        return value == null ? getDefaultValue(): value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getDefaultValue() {
        return (T)key.defaultValue;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }
}
