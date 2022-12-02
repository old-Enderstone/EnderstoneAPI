package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class DoubleUserProperty extends AbstractUserProperty<Double> {

    public DoubleUserProperty(UserProperty key, UUID owner, Double value) {
        super(key, owner, value);
    }

    public abstract Double add(Double value);

    public abstract Double subtract(Double value);

    public abstract Double multiply(Double value);

    public abstract Double divide(Double value);

}
