package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

public abstract class BooleanProperty implements IProperty<Boolean> {

    private final SystemProperty key;
    private Boolean value;

    public BooleanProperty(SystemProperty key, Boolean value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public SystemProperty getKey() {
        return key;
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public void set(Boolean value) {
        this.value = value;
    }
}
