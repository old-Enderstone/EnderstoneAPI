package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class DoubleUserProperty implements IUserProperty<Double> {

    private final UserProperty key;
    private final UUID owner;
    private Double value;

    public DoubleUserProperty(UserProperty key, UUID owner, Double value) {
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
    public Double get() {
        return value;
    }

    @Override
    public void set(Double value) {
        this.value = value;
    }

    @Override
    public Double getDefaultValue() {
        return (Double)key.defaultValue;
    }

    public abstract Double add(Double value);

    public abstract Double subtract(Double value);

    public abstract Double multiply(Double value);

    public abstract Double divide(Double value);

}
