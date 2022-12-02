package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class IntegerProperty extends AbstractSystemProperty<Integer> {

    public IntegerProperty(SystemProperty key, Integer value) {
        super(key, value);
    }

    public abstract Integer add(Integer value);

    public abstract Integer subtract(Integer value);

    public abstract Integer multiply(Integer value);

    public abstract Integer divide(Integer value);
    
}
