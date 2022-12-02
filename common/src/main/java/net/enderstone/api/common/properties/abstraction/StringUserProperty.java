package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class StringUserProperty implements IUserProperty<String> {

    private final UserProperty key;
    private final UUID owner;
    private String value;

    public StringUserProperty(UserProperty key, UUID owner, String value) {
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
    public void set(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
