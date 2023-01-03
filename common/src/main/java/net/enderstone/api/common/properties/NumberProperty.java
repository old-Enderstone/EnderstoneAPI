package net.enderstone.api.common.properties;

import java.util.function.BiFunction;

/**
 * Used to represent number values and comes with add, subtract, multiply and divide methods.
 * These methods should be used instead of {@link AbstractProperty#set(Object)} whenever possible, to prevent
 * issues when two client try to change the value of the same property at the same time.
 * @param <T> Number type, Integer, Float, Long, [...]
 */
public abstract class NumberProperty<T extends Number> extends AbstractProperty<T> {

    public NumberProperty(final PropertyKey<T> key, final T value) {
        super(key, value);
    }

    /**
     * Adds the properties current value and the given value
     * @param number the number to add
     * @return result after adding both numbers
     * @see #performAction(Number, BiFunction) 
     */
    public abstract T add(final T number);

    /**
     * Subtracts the properties current value and the given value
     * @param number the number to subtract
     * @return result after subtracting both numbers
     * @see #performAction(Number, BiFunction) 
     */
    public abstract T subtract(final T number);

    /**
     * Multiplies the properties current value and the given value
     * @param number the number to multiply with
     * @return result after multiplying both numbers
     * @see #performAction(Number, BiFunction) 
     */
    public abstract  T multiply(final T number);

    /**
     * Divides the properties current value and the given value
     * @param number the number to divide by
     * @return result after dividing both numbers
     * @see #performAction(Number, BiFunction) 
     */
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
