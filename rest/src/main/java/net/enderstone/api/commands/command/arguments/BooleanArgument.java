package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;
import net.enderstone.api.utils.Arrays;

public class BooleanArgument extends Argument<Boolean> {

    public BooleanArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.BOOLEAN, required);
    }

    public BooleanArgument(String name, boolean required) {
        super(name, ArgumentType.BOOLEAN, required);
    }

    @Override
    public String[] getAllowedValues() {
        return Arrays.of("true", "false", null);
    }

    @Override
    public Boolean getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue() != null && getDefaultValue();
        return arguments.getBoolean(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        if(value == null) return true;
        return value.toLowerCase().matches("true|false");
    }
}
