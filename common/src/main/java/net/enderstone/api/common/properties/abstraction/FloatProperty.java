package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class FloatProperty extends AbstractSystemProperty<Float> {

    public FloatProperty(SystemProperty key, Float value) {
        super(key, value);
    }

    public abstract Float add(Float value);

    public abstract Float subtract(Float value);

    public abstract Float multiply(Float value);

    public abstract Float divide(Float value);
    
}
