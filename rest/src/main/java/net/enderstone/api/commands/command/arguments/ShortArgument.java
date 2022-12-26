package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class ShortArgument extends Argument<Short> {

    public ShortArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.SHORT, required);
    }

    public ShortArgument(String name, boolean required) {
        super(name, ArgumentType.SHORT, required);
    }

    @Override
    public Short getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getShort(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Short.parseShort(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
