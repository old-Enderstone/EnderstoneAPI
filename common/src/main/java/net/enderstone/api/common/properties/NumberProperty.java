package net.enderstone.api.common.properties;

import net.enderstone.api.common.types.NumberAction;

import java.util.function.BiFunction;

/**
 * Used to represent number values and comes with add, subtract, multiply and divide methods.
 * These methods should be used instead of {@link AbstractProperty#set(Object)} whenever possible, to prevent
 * issues when two client try to change the value of the same property at the same time.
 * @param <T> Number type, Integer, Float, Long, [...]
 */
public abstract class NumberProperty<T extends Number> extends AbstractProperty<T> {

    private transient final NumberAction<T> addAction;
    private transient final NumberAction<T> subtractAction;
    private transient final NumberAction<T> multiplyAction;
    private transient final NumberAction<T> divideAction;

    public NumberProperty(final PropertyKey<T> key,
                          final T value,
                          final NumberAction<T> addAction,
                          final NumberAction<T> subtractAction,
                          final NumberAction<T> multiplyAction,
                          final NumberAction<T> divideAction) {
        super(key, value);
        this.addAction = addAction;
        this.subtractAction = subtractAction;
        this.multiplyAction = multiplyAction;
        this.divideAction = divideAction;
    }

    public abstract T add(final T t1, final T t2);
    public abstract T sub(final T t1, final T t2);
    public abstract T mul(final T t1, final T t2);
    public abstract T div(final T t1, final T t2);

    /**
     * Adds the properties current value and the given value
     * @param number the number to add
     * @return result after adding both numbers
     * @see #performAction(Number, NumberAction)
     */
    public T add(final T number) {
        return performAction(number, addAction);
    }

    /**
     * Subtracts the properties current value and the given value
     * @param number the number to subtract
     * @return result after subtracting both numbers
     * @see #performAction(Number, NumberAction)
     */
    public T subtract(final T number) {
        return performAction(number, subtractAction);
    }

    /**
     * Multiplies the properties current value and the given value
     * @param number the number to multiply with
     * @return result after multiplying both numbers
     * @see #performAction(Number, NumberAction)
     */
    public  T multiply(final T number) {
        return performAction(number, multiplyAction);
    }

    /**
     * Divides the properties current value and the given value
     * @param number the number to divide by
     * @return result after dividing both numbers
     * @see #performAction(Number, NumberAction)
     */
    public T divide(final T number) {
        return performAction(number, divideAction);
    }

    /**
     * Perform an action using the value returned by {@link #get()} and the given number, like adding or subtracting numbers.
     * This method uses a lock to prevent concurrent invocations.
     * This method will <b>not</b> update the properties value, however the given action may update the properties value
     * @param number number to work with
     * @param action action to perform
     * @return result of the action
     */
    public T performAction(final T number, final NumberAction<T> action) {
        lock.lock();

        final T value = action.perform(this, number);

        lock.unlock();
        return value;
    }

}
