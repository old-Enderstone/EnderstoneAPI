package net.enderstone.api.common.properties;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Used to represent a property value. <br> <br>
 *
 * This class represents both user and system properties.
 * A property is a system property if the {@link #owner} field is null
 * @param <T>
 */
public abstract class AbstractProperty<T> {

    protected final PropertyKey<T> key;
    private UUID owner;
    protected T value;

    protected final ReentrantLock lock = new ReentrantLock();

    public AbstractProperty(final PropertyKey<T> key, final UUID owner, final T value) {
        this.key = key;
        this.owner = owner;
        this.value = value;
    }

    public AbstractProperty(final PropertyKey<T> key, final T value) {
        this.key = key;
        this.owner = null;
        this.value = value;
    }

    /**
     * Set the property owner, used by service class.
     * A null value indicates the property is a system property.
     */
    public void setOwner(final @Nullable UUID owner) {
        this.owner = owner;
    }

    /**
     * Casts the property to a NumberProperty of the type Integer
     * @see #asNumberProperty(Class)
     */
    public NumberProperty<Integer> asIntProperty() {
        return asNumberProperty(Integer.class);
    }

    /**
     * Casts the property to a NumberProperty of the type Long
     * @see #asNumberProperty(Class)
     */
    public NumberProperty<Long> asLongProperty() {
        return asNumberProperty(Long.class);
    }

    /**
     * Casts the property to a NumberProperty of the type Float
     * @see #asNumberProperty(Class)
     */
    public NumberProperty<Float> asFloatProperty() {
        return asNumberProperty(Float.class);
    }

    /**
     * Casts the property to a NumberProperty of the type Double
     * @see #asNumberProperty(Class)
     */
    public NumberProperty<Double> asDoubleProperty() {
        return asNumberProperty(Double.class);
    }

    /**
     * Casts the property to a number property
     * @param type number type, Integer.class, Double.class, [...], unused parameter, merely used for generics
     */
    public <P extends Number> NumberProperty<P> asNumberProperty(final Class<P> type) {
        return (NumberProperty<P>) this;
    }

    public PropertyKey<T> getKey() {
        return key;
    }

    /**
     * Get the property owner, if the return value is null, the property is a system property
     */
    public @Nullable UUID getOwner() {
        return owner;
    }

    /**
     * Returns true if the owner field is null
     */
    public boolean isSystemProperty() {
        return owner == null;
    }

    /**
     * Throws runtime exception if value is null and key does not allow null values
     */
    protected void checkNullValue(final boolean isNull) {
        if(isNull && !key.isNullable()) throw new RuntimeException("Null value for property key not accepted.");
    }

    /**
     * Uses the Object.toString() method to turn the value into a string, returns null if the value is null.
     * Used for storing values in database or transmitting values using http
     */
    public String asString() {
        if(this.value == null) return null;
        return this.value.toString();
    }

    /**
     * Parse and set property value from given String.
     * Used for loading values from http requests/responses or database
     */
    public abstract void fromString(final @Nullable String value);

    /**
     * Locks the method, sets the new value, calls the onUpdate hook and then unlocks the method again
     * @param value the new value
     * @throws RuntimeException if the given value is null but the property key does not allow for values to be null
     */
    public void set(final T value) {
        lock.lock();

        checkNullValue(value == null);
        this.value = value;
        this.key.onUpdate().accept(this);

        lock.unlock();
    }

    /**
     * @return the property keys default value
     */
    public T getDefaultValue() {
        return key.defaultValue();
    }

    /**
     * @return the properties current value, if the value is null, the default value is returned using {@link #getDefaultValue()}
     */
    public T get() {
        final T value = this.value;
        if(value == null) {
            return getDefaultValue();
        }
        return value;
    }

}
