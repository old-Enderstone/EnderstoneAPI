package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class BooleanUserProperty implements IUserProperty<Boolean> {

    private final UserProperty key;
    private final UUID owner;
    private Boolean value;

    public BooleanUserProperty(UserProperty key, UUID owner, Boolean value) {
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
    public Boolean get() {
        return value;
    }

    @Override
    public void set(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getDefaultValue() {
        return (Boolean)key.defaultValue;
    }

    public abstract Boolean toggle();

}
