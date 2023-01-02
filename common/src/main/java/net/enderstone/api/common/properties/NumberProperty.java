package net.enderstone.api.common.properties;

import java.util.function.BiFunction;

public abstract class NumberProperty<T extends Number> extends AbstractProperty<T> {

    public NumberProperty(final PropertyKey<T> key, final T value) {
        super(key, value);
    }

    public abstract T add(final T number);

    public abstract T subtract(final T number);

    public abstract  T multiply(final T number);

    public abstract T divide(final T number);

    /**
     * Perform an action using the value returned by {@link #get()} and the given number, like adding or subtracting numbers.
     * This method uses a lock to prevent concurrent invocations.
     * This method will set the actions result as the properties value and return the new value
     * @param number number to work with
     * @param action action to perform
     * @return result of the action
     */
    public T performAction(final T number, final BiFunction<T, T, T> action) {
        lock.lock();

        final T value = action.apply(get(), number);
        set(value);

        lock.unlock();
        return value;
    }

}
