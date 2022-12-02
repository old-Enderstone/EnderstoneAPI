package net.enderstone.api.common.properties;

import java.util.UUID;

public interface IUserProperty<T> {

    UserProperty getKey();
    UUID getOwner();

    /**
     * Get the current value of the property. If there is no value, returns the default value
     */
    T get();
    T getDefaultValue();

    void set(final T value);

}
