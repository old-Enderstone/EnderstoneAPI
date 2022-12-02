package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class StringProperty implements IProperty<String> {

    private final SystemProperty key;
    private String value;

    public StringProperty(SystemProperty key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public void set(String value) {
        this.value = value;
    }
}
