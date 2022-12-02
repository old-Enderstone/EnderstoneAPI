package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class LongProperty extends AbstractSystemProperty<Long> {

    public LongProperty(SystemProperty key, Long value) {
        super(key, value);
    }

    public abstract Long add(Long value);

    public abstract Long subtract(Long value);

    public abstract Long multiply(Long value);

    public abstract Long divide(Long value);
    
}
