package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractUserProperty<T> implements IUserProperty<T> {

    private final UserProperty key;
    private final UUID owner;
    protected T value;

    private transient final Map.Entry<UUID, UserProperty> entry;

    public AbstractUserProperty(UserProperty key, UUID owner, T value) {
        this.key = key;
        this.owner = owner;
        this.value = value;
        this.entry = new AbstractMap.SimpleImmutableEntry<>(getOwner(), getKey());
    }

    public Map.Entry<UUID, UserProperty> toEntry() {
        return entry;
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
    public T get() {
        return value == null ? getDefaultValue(): value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getDefaultValue() {
        return (T)key.defaultValue;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }
}
