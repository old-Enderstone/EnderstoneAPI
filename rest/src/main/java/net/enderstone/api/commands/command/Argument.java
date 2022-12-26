package net.enderstone.api.commands.command;

import java.util.function.Supplier;

public abstract class Argument<T> {

    private final String name;
    private String description = "no description available";

    private T defaultValue = null;

    private final ArgumentType type;
    private final boolean required;

    private Supplier<String[]> allowed = null;

    public Argument(String name, String description, ArgumentType type, boolean required) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
    }

    public Argument(String name, ArgumentType type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public final Argument<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final ArgumentType getType() {
        return type;
    }

    public final boolean isRequired() {
        return required;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public Argument<T> setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Argument<T> setAllowedValues(String... allowed) {
        this.allowed = () -> allowed;
        return this;
    }

    public Argument<T> setAllowedValues(Supplier<String[]> supplier){
        this.allowed = supplier;
        return this;
    }

    public String[] getAllowedValues() {
        return allowed != null ? allowed.get(): null;
    }

    public abstract T getValue(CommandArguments arguments);

    public abstract boolean isValidValue(String value);

}
