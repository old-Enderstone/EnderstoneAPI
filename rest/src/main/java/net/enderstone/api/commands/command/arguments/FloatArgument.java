package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class FloatArgument extends Argument<Float> {

    public FloatArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.FLOAT, required);
    }

    public FloatArgument(String name, boolean required) {
        super(name, ArgumentType.FLOAT, required);
    }

    @Override
    public Float getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getFloat(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
