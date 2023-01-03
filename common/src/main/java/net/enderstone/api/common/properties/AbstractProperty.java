package net.enderstone.api.common.properties;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractProperty<T> {

    protected final PropertyKey<T> key;
    private final UUID owner;
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

    public PropertyKey<T> getKey() {
        return key;
    }

    public @Nullable UUID getOwner() {
        return owner;
    }

    public boolean isSystemProperty() {
        return owner == null;
    }

    /**
     * Throws runtime exception if value is null and key does not allow null values
     */
    protected void checkNullValue(final boolean isNull) {
        if(isNull && !key.isNullable()) throw new RuntimeException("Null value for property key not accepted.");
    }

    public String asString() {
        return this.value.toString();
    }

    /**
     * Parse and set property value from given String
     */
    public abstract void fromString(final @Nullable String value);

    public void set(final T value) {
        lock.lock();

        checkNullValue(value == null);
        this.value = value;
        this.key.onUpdate().accept(this);

        lock.unlock();
    }

    public T getDefaultValue() {
        return key.defaultValue();
    }

    public T get() {
        final T value = this.value;
        if(value == null) {
            return getDefaultValue();
        }
        return value;
    }

}
