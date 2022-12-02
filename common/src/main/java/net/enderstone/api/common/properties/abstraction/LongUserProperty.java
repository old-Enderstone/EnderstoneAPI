package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class LongUserProperty implements IUserProperty<Long> {

    private final UserProperty key;
    private final UUID owner;
    private Long value;

    public LongUserProperty(UserProperty key, UUID owner, Long value) {
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
    public Long get() {
        return value;
    }

    @Override
    public void set(Long value) {
        this.value = value;
    }

    @Override
    public Long getDefaultValue() {
        return (Long)key.defaultValue;
    }

    public abstract Long add(Long value);

    public abstract Long subtract(Long value);

    public abstract Long multiply(Long value);

    public abstract Long divide(Long value);

}
