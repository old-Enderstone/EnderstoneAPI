package net.enderstone.api.common;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class Player {

    protected final UUID id;
    protected String lastKnownName;
    protected final Collection<IUserProperty<?>> properties;

    public Player(UUID id, String lastKnownName, Collection<IUserProperty<?>> properties) {
        this.id = id;
        this.lastKnownName = lastKnownName;
        this.properties = properties;
    }

    /**
     * Gets a user property or creates a new one using the given default value, if the property does not yet exist.
     */
    public abstract IUserProperty<?> getProperty(UserProperty property);

    public void setLastKnownName(String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public UUID getId() {
        return id;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public Collection<IUserProperty<?>> getProperties() {
        return List.copyOf(properties);
    }
}
