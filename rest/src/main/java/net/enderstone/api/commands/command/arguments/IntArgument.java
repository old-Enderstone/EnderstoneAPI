package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class IntArgument extends Argument<Integer> {

    public IntArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.INT, required);
    }

    public IntArgument(String name, boolean required) {
        super(name, ArgumentType.INT, required);
    }

    @Override
    public Integer getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getInt(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
