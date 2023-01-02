package net.enderstone.api.common.properties;

import java.util.function.Consumer;
import java.util.function.Function;

public class PropertyKey<T> {

    private final String identifier;
    private final T defaultValue;
    private final boolean isNullable;
    private final Function<PropertyKey<T>, AbstractProperty<T>> supplier;
    private final Consumer<AbstractProperty<T>> onUpdate;

    public PropertyKey(final String identifier,
                       final T defaultValue,
                       final boolean isNullable,
                       final Function<PropertyKey<T>, AbstractProperty<T>> supplier,
                       final Consumer<AbstractProperty<T>> onUpdate) {
        this.identifier = identifier;
        this.defaultValue = defaultValue;
        this.isNullable = isNullable;
        this.supplier = supplier;
        this.onUpdate = onUpdate;
    }

    public String identifier() {
        return identifier;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public Function<PropertyKey<T>, AbstractProperty<T>> supplier() {
        return supplier;
    }

    public Consumer<AbstractProperty<T>> onUpdate() {
        return onUpdate;
    }

}
