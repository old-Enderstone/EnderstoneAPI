package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class IntegerUserProperty implements IUserProperty<Integer> {

    private final UserProperty key;
    private final UUID owner;
    private Integer value;

    public IntegerUserProperty(UserProperty key, UUID owner, Integer value) {
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
    public Integer get() {
        return value;
    }

    @Override
    public void set(Integer value) {
        this.value = value;
    }
}
