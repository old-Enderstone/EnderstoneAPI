package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class FloatUserProperty extends AbstractUserProperty<Float> {

    public FloatUserProperty(UserProperty key, UUID owner, Float value) {
        super(key, owner, value);
    }

    public abstract Float add(Float value);

    public abstract Float subtract(Float value);

    public abstract Float multiply(Float value);

    public abstract Float divide(Float value);

}
