package net.enderstone.api.common.properties;

public interface IProperty<T> {

    SystemProperty getKey();

    /**
     * Get the current value of the property. If there is no value, returns the default value
     */
    T get();
    T getDefaultValue();

    void set(final T value);
}
