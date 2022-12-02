package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class LongUserProperty extends AbstractUserProperty<Long> {

    public LongUserProperty(UserProperty key, UUID owner, Long value) {
        super(key, owner, value);
    }

    public abstract Long add(Long value);

    public abstract Long subtract(Long value);

    public abstract Long multiply(Long value);

    public abstract Long divide(Long value);

}
