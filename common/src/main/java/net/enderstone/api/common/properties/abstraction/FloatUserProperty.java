package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class FloatUserProperty implements IUserProperty<Float> {

    private final UserProperty key;
    private final UUID owner;
    private Float value;

    public FloatUserProperty(UserProperty key, UUID owner, Float value) {
        this.key = key;
        this.owner = owner;
        this.value = value;
    }

    @Override
    public UserProperty getKey() {
        return key;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public void set(Float value) {
        this.value = value;
    }

    @Override
    public Float getDefaultValue() {
        return (Float)key.defaultValue;
    }

    public abstract Float add(Float value);

    public abstract Float subtract(Float value);

    public abstract Float multiply(Float value);

    public abstract Float divide(Float value);

}
